package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.MarcaModeloService;
import ni.gob.inss.sisinv.bussineslogic.service.seguridad.UsuarioExtService;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;

/**
 * <b>SISINV</b></br>
 * <b>Copyright (c) 2016 MEFCCA.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * </br>
 *
 * @author CRISTHIAM JESUS MERCADO OBANDO
 * @version 1.0, 14/10/2016
 * @since 1.0 *
 */


@Named
@Scope("view")
public class MarcaModeloBackBean extends BaseBackBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String txtBusquedaMarcaByNombre;
    private boolean nuevoRegistro;
    private boolean pasivo;
    
    private MarcasModelos marcaSeleccionada;

    private List<MarcasModelos> listaMarcas;
    private List<MarcasModelos> listaModelos;
    private boolean autorizadoParaEditar;

    private MarcasModelos modeloSeleccionada;
    private MarcasModelos oMarca;
    private MarcasModelos oModelo;


    @Autowired
    MarcaModeloService oMarcaModeloService;


    @Autowired
    UsuarioExtService oUsuarioService;

    @Autowired
    AuditoriaService oDialogAuditoriaService;


    @PostConstruct
    public void init() {
        this.limpiar();
        this.oMarca = new MarcasModelos();
        this.buscarMarcaByName();
        this.autorizadoParaEditar = oUsuarioService.usuarioTieneAutorizacion(this.getUsuarioActual(), this.getEntidadActual(), "EDEL");
    }

    public void limpiar() {
    	this.setoMarca(new MarcasModelos());
    	this.setoModelo(new MarcasModelos());
        this.setTxtBusquedaMarcaByNombre("");
        this.setNuevoRegistro(true);
        this.setMarcaSeleccionada(null);
    }

    public void buscarMarcaByName() {
        try {
        	//TODO:REFACTORIZAR, EL 0 EN ESTE METODO SE REFIERE A QUE BUSCAMOS A LAS MARCAS
            this.listaMarcas = oMarcaModeloService.buscarMarcasOModelos(this.getTxtBusquedaMarcaByNombre(), NumberUtils.INTEGER_ZERO, null);
            if (this.listaMarcas.isEmpty()) {
                mostrarMensajeInfo("No se han encontrado resultados con el criterio de Búsqueda ingresada.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "buscarMarcaByName", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void editar() {
        if (this.getMarcaSeleccionada() != null) {
        	this.setoMarca(this.getMarcaSeleccionada());
            this.buscarModeloPorMarca(this.getMarcaSeleccionada().getId());
        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    public void guardarOrActualizar() {
        if (this.getoMarca().getId() == null) {
            this.guardar();
        } else {
            this.actualizar();
        }
        this.setTxtBusquedaMarcaByNombre("");
        this.buscarMarcaByName();
    }

     public void guardar() {
        try {
            oMarca.setCreadoPor(this.getUsuarioActual().getId());
            oMarca.setCreadoEl(this.getTimeNow());
            oMarca.setCreadoEnIp(this.getRemoteIp());
            oMarca.setPasivo(false);
            oMarca.setPadreId(0);
            oMarcaModeloService.agregar(oMarca);
            mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardarNuevaMarca", MessagesResults.ERROR_GUARDAR, e);

        }
    }

    public void actualizar() {
        try {
        	oMarca.setModificadoEl(this.getTimeNow());
            oMarca.setModificadoPor(this.getUsuarioActual().getId());
            oMarca.setModificadoEnIp(this.getRemoteIp());
            oMarcaModeloService.actualizar(oMarca);
            mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizar", MessagesResults.ERROR_MODIFICAR, e);
        }
    }

    public void buscarModeloPorMarca(Integer idMarca) {
        try {
            this.listaModelos = oMarcaModeloService.buscarMarcasOModelos(StringUtils.defaultString(""),this.getoMarca().getId(), null);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "buscarModeloPorMarca", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void guardarModelos() {
        try {
            //Por ser nueva Marca por defecto es activo.
            this.oModelo.setCreadoPor(this.getUsuarioActual().getId());
            this.oModelo.setCreadoEl(this.getTimeNow());
            this.oModelo.setCreadoEnIp(this.getRemoteIp());
            this.oModelo.setPasivo(false);
            this.oModelo.setPadreId(oMarca.getId());
            oMarcaModeloService.agregar(oModelo);
            mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardarNuevaModelos", MessagesResults.ERROR_GUARDAR, e);

        }
    }

    public void limpiarModelo() {
    	this.setoModelo(new MarcasModelos());
        this.setModeloSeleccionada(null);
    }
    
    public void agregarModelo() {
        try {
            limpiarModelo();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarModelo", MessagesResults.ERROR_OBTENER, e);
        }
    }


    public void guardarModelo() {
        try {
            guardarModelos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardarModelo", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void cargarDatosModelo() {
        if (this.getModeloSeleccionada() != null) {
        	this.setoModelo(this.getModeloSeleccionada());
        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    public void guardarOrActualizarModelo() {
        if (this.getoModelo().getId() == null) {
            this.guardarModelo();
        } else {
            this.actualizarModelo();
        }
        RequestContext.getCurrentInstance().execute("PF('dlgmarca').hide()");
        this.buscarModeloPorMarca(this.getoMarca().getId());
    }

    public void actualizarModelo() {
        try {
            this.oModelo.setModificadoEl(this.getTimeNow());
            this.oModelo.setModificadoPor(this.getUsuarioActual().getId());
            oModelo.setModificadoEnIp(this.getRemoteIp());
            oMarcaModeloService.actualizar(oModelo);
            mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);

        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizarModelo", MessagesResults.ERROR_MODIFICAR, e);
        }
    }


    public String getTxtBusquedaMarcaByNombre() {
        return StringUtils.defaultString(txtBusquedaMarcaByNombre);
    }

    public void setTxtBusquedaMarcaByNombre(String txtBusquedaMarcaByNombre) {
        this.txtBusquedaMarcaByNombre = txtBusquedaMarcaByNombre;
    }

    public boolean isNuevoRegistro() {
        return nuevoRegistro;
    }

    public void setNuevoRegistro(boolean nuevoRegistro) {
        this.nuevoRegistro = nuevoRegistro;
    }


    public boolean isPasivo() {
        return pasivo;
    }

    public void setPasivo(boolean pasivo) {
        this.pasivo = pasivo;
    }

    public MarcasModelos getMarcaSeleccionada() {
        return marcaSeleccionada;
    }

    public void setMarcaSeleccionada(MarcasModelos marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }

    public List<MarcasModelos> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<MarcasModelos> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public boolean isAutorizadoParaEditar() {
        return autorizadoParaEditar;
    }

    public void setAutorizadoParaEditar(boolean autorizadoParaEditar) {
        this.autorizadoParaEditar = autorizadoParaEditar;
    }

    public List<MarcasModelos> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<MarcasModelos> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public MarcasModelos getModeloSeleccionada() {
        return modeloSeleccionada;
    }

    public void setModeloSeleccionada(MarcasModelos modeloSeleccionada) {
        this.modeloSeleccionada = modeloSeleccionada;
    }

	public MarcasModelos getoMarca() {
		return oMarca;
	}

	public void setoMarca(MarcasModelos oMarca) {
		this.oMarca = oMarca;
	}

	public MarcasModelos getoModelo() {
		return oModelo;
	}

	public void setoModelo(MarcasModelos oModelo) {
		this.oModelo = oModelo;
	}
	
}
