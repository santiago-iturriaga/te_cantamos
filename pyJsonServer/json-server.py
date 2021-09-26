#!/usr/bin/python3

from http.server import HTTPServer, BaseHTTPRequestHandler
from http import HTTPStatus
import json
import time
#import sqlite3
import mariadb
import sys
import ssl

DEBUG = 0
LOGOUT_CLIENTS = False

con = None

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
            if not message['ps'] == 'lola':
                print('[ERROR] password incorrecta: {0}'.format(message['ps']), file=sys.stderr)
                self._set_headers()
                self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))
                return
            else:
                print('[INFO] password OK', file=sys.stderr)

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
                print('[ERROR] inserting DB: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), file=sys.stderr)
                cur.close()
                con.rollback()
                self._set_headers()
                self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))
        except:
            print('[ERROR] parsing JSON: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), file=sys.stderr)
            self._set_headers()
            self.wfile.write(json.dumps({'success': False, 'logout': LOGOUT_CLIENTS}).encode('utf-8'))                            

    def do_OPTIONS(self):
        # Send allow-origin header for preflight POST XHRs.
        self.send_response(HTTPStatus.NO_CONTENT.value)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST')
        self.send_header('Access-Control-Allow-Headers', 'content-type')
        self.end_headers()

def run(port=8008):
    global con
    
    #db='app.db'
    #print('Starting sqlite3 on file {0}...'.format(db))
    #con = sqlite3.connect(db)
    #cur = con.cursor()
    # Get Cursor
    #cur = conn.cursor()
    #try:
    #    cur.execute('''CREATE TABLE log (usuario TEXT, fecha INTEGER, nombre_cancion TEXT, numero_cancion INTEGER, inicio INTEGER, fin INTEGER)''')
    #except:
    #    print('[WARNING] creating DB: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), file=sys.stderr)
    #cur.close()
    
    print('Starting mariadb on connection...')
    try:
        con = mariadb.connect(
            user="mama",
            password="chorlito",
            host="localhost",
            port=3306,
            database="mama_te_canta"

        )
        con.autocommit = False
    except mariadb.Error:
        print('[ERROR] conectandose a mariadb: {0} <{1}>'.format(sys.exc_info()[1],sys.exc_info()[0]), file=sys.stderr)
        sys.exit(1)

    print('Starting httpd on port {0}...'.format(port))
    server_address = ('', port)
    httpd = HTTPServer(server_address, _RequestHandler)
    httpd.socket = ssl.wrap_socket(httpd.socket, certfile='cert.pem', keyfile='key.pem', server_side=True)
    httpd.serve_forever()

    #httpd = HTTPServer(('localhost', 1443), SimpleHTTPRequestHandler)

if __name__ == "__main__":
    from sys import argv

    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()
