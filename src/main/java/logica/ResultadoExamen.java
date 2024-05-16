package logica;

import org.opencv.core.Point;

public class ResultadoExamen {
    private String pregunta;
    private String respuestaSeleccionada;
    private String respuestaCorrecta;

    public ResultadoExamen(String pregunta, String respuestaSeleccionada, String respuestaCorrecta) {
        this.pregunta = pregunta;
        this.respuestaSeleccionada = respuestaSeleccionada;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // Getters
    public String getPregunta() {
        return pregunta;
    }

    public String getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

	public Point getCentro() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRadio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isCorrecta() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
