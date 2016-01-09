package net.servi.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.servi.adapter.AdapterAvailableNow;
import net.servi.dao.DaoHorario;
import net.servi.dto.DtoHorario;
import android.widget.ListAdapter;

public class ModelAvailableNow {

	public static final int MONDAY = 2, TUESDAY = 3, WEDNESDAY = 4,
			THURSDAY = 5, FRIDAY = 6;
	private DaoHorario daoHorario;
	private List<DtoHorario> lstDtoHorarios;
	private AdapterAvailableNow adapterAvailableNow;
	private int day;

	public ModelAvailableNow() {
		daoHorario = new DaoHorario();
		lstDtoHorarios = new ArrayList<DtoHorario>();
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		day = c.get(Calendar.DAY_OF_WEEK);
		if (day > 1 & day < 7) {
			if(c.get(Calendar.HOUR_OF_DAY)<12){
				lstDtoHorarios = daoHorario.SelectAvailableRooms(getDayString(), "AM");
			} else {
				lstDtoHorarios = daoHorario.SelectAvailableRooms(getDayString(), "PM");
			}
		}
	}

	public ListAdapter getAdapterList() {
		return adapterAvailableNow = new AdapterAvailableNow(lstDtoHorarios);
	}

	public int getSizeList(){
		return lstDtoHorarios.size();
	}
	
	private String getDayString() {
		switch (day) {
		case MONDAY:
			return "lunes";
		case TUESDAY:
			return "martes";
		case WEDNESDAY:
			return "miercoles";
		case THURSDAY:
			return "jueves";
		case FRIDAY:
			return "viernes";
		default:
			return null;
		}
	}
}
