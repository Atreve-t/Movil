package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HorarioGarage {
    private List<Periodo> periodos;

    public HorarioGarage() {
        periodos = new ArrayList<>();
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void agregarHorario(int dia, int mes, int año, Calendar horaInicio, Calendar horaFin) {
        // Verificar si ya existe un periodo para esta fecha
        Periodo periodoExistente = buscarPeriodoPorFecha(dia, mes, año);
        if (periodoExistente != null) {
            // Agregar el horario al periodo existente
            periodoExistente.agregarHorario(horaInicio, horaFin);
        } else {
            // Crear un nuevo periodo y agregar el horario
            Periodo nuevoPeriodo = new Periodo();
            nuevoPeriodo.setDia(dia);
            nuevoPeriodo.setMes(mes);
            nuevoPeriodo.setAño(año);
            nuevoPeriodo.agregarHorario(horaInicio, horaFin);
            periodos.add(nuevoPeriodo);
        }
    }

    private Periodo buscarPeriodoPorFecha(int dia, int mes, int año) {
        for (Periodo periodo : periodos) {
            if (periodo.getDia() == dia && periodo.getMes() == mes && periodo.getAño() == año) {
                return periodo;
            }
        }
        return null;
    }

    private boolean sonMismoDia(Calendar fecha1, Calendar fecha2) {
        return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
                fecha1.get(Calendar.MONTH) == fecha2.get(Calendar.MONTH) &&
                fecha1.get(Calendar.DAY_OF_MONTH) == fecha2.get(Calendar.DAY_OF_MONTH);
    }

    public static class Periodo {
        private int dia;
        private int mes;
        private int año;
        private List<Hora> horarios;

        public Periodo() {
            horarios = new ArrayList<>();
        }

        public int getDia() {   return dia; }

        public void setDia(int dia) {   this.dia = dia; }

        public int getMes() {   return mes; }

        public void setMes(int mes) {   this.mes = mes; }

        public int getAño() {   return año; }

        public void setAño(int año) {   this.año = año; }

        public List<Hora> getHorarios() {
            return horarios;
        }

        public void agregarHorario(Calendar horaInicio, Calendar horaFin) {
            Hora horario = new Hora();
            horario.setHoraInicio(horaInicio.get(Calendar.HOUR_OF_DAY));
            horario.setMinutoInicio(horaInicio.get(Calendar.MINUTE));
            horario.setHoraFin(horaFin.get(Calendar.HOUR_OF_DAY));
            horario.setMinutoFin(horaFin.get(Calendar.MINUTE));
            horarios.add(horario);
        }
    }

    public static class Hora {
        private int horaInicio;
        private int minutoInicio;
        private int horaFin;
        private int minutoFin;

        public int getHoraInicio() {
            return horaInicio;
        }

        public void setHoraInicio(int horaInicio) {
            this.horaInicio = horaInicio;
        }

        public int getMinutoInicio() {
            return minutoInicio;
        }

        public void setMinutoInicio(int minutoInicio) {
            this.minutoInicio = minutoInicio;
        }

        public int getHoraFin() {
            return horaFin;
        }

        public void setHoraFin(int horaFin) {
            this.horaFin = horaFin;
        }

        public int getMinutoFin() {
            return minutoFin;
        }

        public void setMinutoFin(int minutoFin) {
            this.minutoFin = minutoFin;
        }
    }
}
