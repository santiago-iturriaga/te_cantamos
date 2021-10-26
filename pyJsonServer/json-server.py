#!/usr/bin/python3

from http.server import HTTPServer, BaseHTTPRequestHandler
from http import HTTPStatus
import json
import time
import mariadb
import sys
import ssl
import datetime

PORT=8008
DEBUG = 0
LOGOUT_CLIENTS = False
PASSWORD = 'musica'

def print_log(message, is_error=False):
    time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    if is_error:
        print('[{0}] <INFO> {1}'.format(time,message), flush=True)
    else:
        print('[{0}] <ERROR> {1}'.format(time,message), file=sys.stderr, flush=True)

class _RequestHandler(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(HTTPStatus.OK.value)
        self.send_header('Content-type', 'application/json')
        # Allow requests from any origin, so CORS policies don't
        # prevent local development.
        self.send_header('Access-Control-Allow-Origin', '*')
        self.end_headers()

    def do_GET(self):
        self._set_headers()
        self.wfile.write(json.dumps(_g_posts).encode('utf-8'))

    def do_POST(self):
        length = int(self.headers.get('content-length'))
        raw = self.rfile.read(length)
        message = json.loads(raw)

        if DEBUG:
            print(length)
            print(raw)
            print(message)

        try:
            if not message['ps'] == PASSWORD:
                print_log('incorrect password: {0}'.format(message['ps']), is_error=True)
                self._set_headers()
                self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))
                return
            else:
                #print_log('password OK')
                pass

            con = None
            #print_log('starting mariadb on connection...')
            try:
                con = mariadb.connect(
                    user="mama",
                    password="chorlito",
                    host="localhost",
                    port=3306,
                    database="mama_te_canta"

                )
                con.autocommit = False
                #print_log('mariadb connected!')
            except mariadb.Error:
                print_log('error connecting to mariadb: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), is_error=True)
                sys.exit(1)

            cur = con.cursor()
            try:               
                for item in message['log']:
                    fechaAccion = item['fechaAccion']
                    nombreCancion = item['nombreCancion']
                    numeroCancion = item['numeroCancion']
                    accionInicio = item['accionInicio']
                    accionFin = item['accionFin']
                    
                    sql_insert = "INSERT INTO log VALUES ('{0}',{1},'{2}',{3},{4},{5},null);".format(message['usuario'],fechaAccion,nombreCancion,numeroCancion,accionInicio,accionFin)
                    
                    if DEBUG:
                        print(sql_insert)

                    cur.execute(sql_insert)

                cur.close()
                con.commit()
                self._set_headers()
                self.wfile.write(json.dumps({'success': True, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))
            except:
                print_log('error inserting DB: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), is_error=True)
                cur.close()
                con.rollback()
                self._set_headers()
                self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))

            con.close()
        except:
            print_log('error parsing JSON: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), is_error=True)
            self._set_headers()
            self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))                            

    def do_OPTIONS(self):
        # Send allow-origin header for preflight POST XHRs.
        self.send_response(HTTPStatus.NO_CONTENT.value)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST')
        self.send_header('Access-Control-Allow-Headers', 'content-type')
        self.end_headers()

def run(path='.'):
    print_log('starting httpd on port {0}...'.format(PORT))
    server_address = ('', PORT)
    httpd = HTTPServer(server_address, _RequestHandler)
    httpd.socket = ssl.wrap_socket(httpd.socket, certfile='{0}/cert.pem'.format(path), keyfile='{0}/key.pem'.format(path), server_side=True)
    httpd.serve_forever()
    print_log('httpd started!')

if __name__ == "__main__":
    from sys import argv

    if len(argv) == 2:
        run(path=argv[1])
    else:
        run()
