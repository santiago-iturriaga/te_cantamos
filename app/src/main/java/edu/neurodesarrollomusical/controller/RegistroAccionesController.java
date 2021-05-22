package edu.neurodesarrollomusical.controller;

import android.content.Context;

import java.util.Date;
import java.util.List;

import edu.neurodesarrollomusical.db.RegistroAccionEntity;

public class RegistroAccionesController {
    private static RegistroAccionesController _instance;

    public synchronized static RegistroAccionesController getInstance(Context context) {
        if (_instance == null) {
            _instance = new RegistroAccionesController(context);
        }
        return _instance;
    }

    Context _context;

    private RegistroAccionesController(Context context) {
        _context = context;
    }

    public void crearRegistroInicioCancion(String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date(), cancionTitulo, cancionNumero, true, false));
    }

    public void crearRegistroFinCancion(String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date(), cancionTitulo, cancionNumero, false, true));
    }

    public List<RegistroAccionEntity> obtenerBatchRegistro() {
        return AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().getAllBatch();
    }
}
