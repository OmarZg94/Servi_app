package net.servi.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.gshp.APINetwork.NetworkTask;
import net.servi.dao.DaoHorario;
import net.servi.dao.DaoReporte;
import net.servi.dto.DtoHorario;
import net.servi.dto.DtoReporte;
import net.servi.util.Config;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ModelDatabaseSync {
	
	private final int STORAGE_OK=1;
	
	private DaoHorario daoHorario;
	private DaoReporte daoReporte;
	
	private List<DtoHorario> lstDtoHorarios;
	private List<DtoReporte> lstDtoReportes;
	
	private Handler handlerstorage;

	private ExecutorService executor;

	public ModelDatabaseSync(Handler handlerstorage) {
		executor = Executors.newSingleThreadExecutor();
		this.handlerstorage=handlerstorage;

		daoHorario=new DaoHorario();
		daoReporte=new DaoReporte();
	}

	protected void syncInsertion(final NetworkTask nt) {
		Runnable command = new Runnable() {

			@Override
			public void run() {
				Type typeObjectGson;
				Log.e(Config.TAG, "Tag from NetworkTask: "+nt.getTag());
				if (nt.getTag().equals("horario")) {
					Log.d("SYNC", "horario " + nt.getResponse());
					typeObjectGson = new TypeToken<List<DtoHorario>>() {
					}.getType();
					lstDtoHorarios = new Gson().fromJson(nt.getResponse(),
							typeObjectGson);
					daoHorario.delete();
					daoHorario.Insert(lstDtoHorarios);
				} else if (nt.getTag().equals("reporte")) {
					Log.d("SYNC", "reporte " + nt.getResponse());
					typeObjectGson = new TypeToken<List<DtoReporte>>() {
					}.getType();
					lstDtoReportes = new Gson().fromJson(nt.getResponse(),
							typeObjectGson);
					daoReporte.delete();
					daoReporte.Insert(lstDtoReportes);
				} /* else
				
				// NO CONTENT  solo se debe de borrar
				if (nt.getTag().equals("DELETEc_sku_price")) {
					daoCSkuPrice.delete();
				} else if (nt.getTag().equals("DELETEsku_sku")) {
					daoCSku.delete();
				} else if (nt.getTag().equals("DELETEc_availability")) {
					daoCSkuAvailability.delete();
				} else if (nt.getTag().equals("DELETEc_type_mechanic")) {
					daoCtypeMechanic.delete();
				}else if (nt.getTag().equals("DELETEc_exhibition")) {
					daoCtypeExhibition.delete();
				}else if (nt.getTag().equals("DELETEc_type_report")) {
					daoCtypeReport.delete();
				}else if (nt.getTag().equals("DELETEc_frequency_price")) {
					daoCFrecuencyPrice.delete();
				} else if (nt.getTag().equals("DELETEc_sku_baes")) {
					daoCSkuBaes.delete();
				} else if (nt.getTag().equals("DELETEea_poll")) {
					daoeaEncuesta.delete();
				} else if (nt.getTag().equals("DELETEea_question")) {
					daoeaPregunta.delete();
				} else if (nt.getTag().equals("DELETEea_question_option")) {
					daoeaOpcionPregunta.delete();
				} else if (nt.getTag().equals("DELETEea_question_type_cat")) {
					daoeaTypeOpcionRespuesta.delete();
				}else if (nt.getTag().equals("DELETEpdv_pdv")) {
					daoPdv.delete();
				}else if (nt.getTag().equals("DELETEc_client")) {
					daoCliente.delete();
				}else if (nt.getTag().equals("DELETEc_rtm")) {
					daoRtm.delete();
				}else if (nt.getTag().equals("DELETEc_canal")) {
					daoCanal.delete();
				}else if (nt.getTag().equals("DELETEc_category")) {
					daoCCategory.delete();
				}else if (nt.getTag().equals("DELETEc_manufacturer")) {
					daoCManufacturer.delete();
				}else if (nt.getTag().equals("DELETEc_brand")) {
					daoCBrand.delete();
				}else if (nt.getTag().equals("DELETEc_subcategory")) {
					daoCSubcategory.delete();
				}else if (nt.getTag().equals("DELETEops")) {
					daoOnePager.delete();
				}else if (nt.getTag().equals("DELETEopactividad")) {
					daoOnePagerActivity.delete();
				}else if (nt.getTag().equals("DELETEopa-comentario")) {
					daoOnePagerActivityComment.delete();
				}else if (nt.getTag().equals("DELETEopclients")) {
					daoOnePagerClient.delete();
				}else if (nt.getTag().equals("DELETEoprtms")) {
					daoOnePagerRtm.delete();
				}else if (nt.getTag().equals("DELETEopformat")) {
					daoOnePagerFormat.delete();
				}else if (nt.getTag().equals("DELETEoppdv")) {
					daoOnePagerPdv.delete();
				}else if(nt.getTag().equals("DELETEc_client_format")) {
					daoCClientFormat.delete();
				}else if (nt.getTag().equals("DELETEreport_subordinate")) {
					DaoReportSubordinate dao=new DaoReportSubordinate();
					dao.delete();
				}else if (nt.getTag().equals("DELETEsup_subordinates")) {
					DaoSubordinate dao=new DaoSubordinate();
					dao.delete();
				}else if (nt.getTag().equals("DELETEsup_pdv")) {
					daoPdv.delete();
				}else if (nt.getTag().equals("DELETEsup_client")) {
					daoCliente.delete();
				}else if (nt.getTag().equals("DELETEsup_availability")) {
					Log.d("SYNC", "DELETEsup_availability" + nt.getResponse());
					DaoReportSubordinateAvailability daoAv=new DaoReportSubordinateAvailability();
					daoAv.delete();
				}else if (nt.getTag().equals("DELETEsup_caducity")) {
					Log.d("SYNC", "DELETEsup_caducity" + nt.getResponse());
					DaoReportSubordinateCaducity daoAv=new DaoReportSubordinateCaducity();
					daoAv.delete();
				}else if (nt.getTag().equals("DELETEsup_activity")) {
					Log.d("SYNC", "DELETEsup_activity" + nt.getResponse());
					DaoReportSubordinateActivity daoAv=new DaoReportSubordinateActivity();
					daoAv.delete();
				}else if (nt.getTag().equals("DELETEsup_comment")) {
					Log.d("SYNC", "DELETEsup_comment" + nt.getResponse());
					DaoReportSubordinateComment daoAv=new DaoReportSubordinateComment();
					daoAv.delete();
				}else if (nt.getTag().equals("DELETEc_type_photo")) {
					Log.d("SYNC", "DELETEc_type_photo" + nt.getResponse());
					DaoCTypePhoto dao=new DaoCTypePhoto();
					dao.delete();
				}else if (nt.getTag().equals("DELETEc_subtype_photo")) {
					Log.d("SYNC", "DELETEc_subtype_photo" + nt.getResponse());
					DaoCSubTypePhoto dao=new DaoCSubTypePhoto();
					dao.delete();
				}
				*/
				handlerstorage.sendEmptyMessage(STORAGE_OK);
			}
		};
		executor.execute(command);
	}
}
