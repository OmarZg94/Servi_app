package net.servi.dto;

public class DtoReporte {

	private int id, idHorario, estado;
	private String ipAlumno, reportedOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getIpAlumno() {
		return ipAlumno;
	}

	public void setIpAlumno(String ipAlumno) {
		this.ipAlumno = ipAlumno;
	}

	public String getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(String reportedOn) {
		this.reportedOn = reportedOn;
	}
}
