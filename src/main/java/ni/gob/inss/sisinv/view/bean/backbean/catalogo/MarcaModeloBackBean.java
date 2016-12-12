package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.MarcaModeloService;
import ni.gob.inss.sisinv.bussineslogic.service.seguridad.UsuarioExtService;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Integer hfId;
    private boolean pasivo;
    private String descripcionMarca;
    private MarcasModelos marcaSeleccionada;

    private List<MarcasModelos> listaMarcas;
    private List<MarcasModelos> listaModelos;
    private boolean autorizadoParaEditar;

    private boolean btnElminiarVisible;
    private boolean btnAuditoriaVisible;

    private boolean nuevoRegistroModelo;
    private Integer hfIdModelo;
    private boolean pasivoModelo;
    private String descripcionModelo;
    private MarcasModelos modeloSeleccionada;

    private List listaAuditoria;
    private String tituloDialog;


    @Autowired
    MarcaModeloService oMarcaModeloService;


    @Autowired
    UsuarioExtService oUsuarioService;

    @Autowired
    AuditoriaService oDialogAuditoriaService;


    @PostConstruct
    public void init() {
        this.limpiar();
        this.buscarMarcaByName();
        this.autorizadoParaEditar = oUsuarioService.usuarioTieneAutorizacion(this.getUsuarioActual(), this.getEntidadActual(), "EDEL");
    }

    public void limpiar() {
        this.setTxtBusquedaMarcaByNombre("");
        this.setNuevoRegistro(true);
        this.setHfId(null);
        this.setDescripcionMarca("");
        this.setPasivo(false);
        this.setMarcaSeleccionada(null);
        this.setBtnElminiarVisible(false);
        this.setBtnAuditoriaVisible(false);
        this.setDescripcionModelo("");

    }

    public void buscarMarcaByName() {
        try {
            this.listaMarcas = oMarcaModeloService.buscar(this.txtBusquedaMarcaByNombre, "MARCA", 0);
            if (this.listaMarcas.isEmpty()) {
                mostrarMensajeInfo("No se han encontrado resultados con el criterio de Búsqueda ingresada.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "buscarMarcaByName", MessagesResults.ERROR_OBTENER_LISTA, e);
        }

    }

    public void editar() {
        if (this.getMarcaSeleccionada() != null) {
            this.cargarDatosMarca(this.getMarcaSeleccionada().getId());
            this.buscarModeloPorMarca(this.getMarcaSeleccionada().getId());
            this.setBtnElminiarVisible(true);
            this.setBtnAuditoriaVisible(true);
        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }

    }

    public void guardarOrActualizar() {
        if (this.getHfId() == null) {
            this.guardar();
        } else {
            this.actualizar();
        }
        this.cargarDatosMarca(this.getHfId());
        this.setTxtBusquedaMarcaByNombre("");
        this.buscarMarcaByName();
    }

    public void cargarDatosMarca(Integer marcaId) {
        try {
            MarcasModelos oMarcas = oMarcaModeloService.obtener(marcaId);
            this.setDescripcionMarca(oMarcas.getDescripcion());
            this.setHfId(oMarcas.getId());
            this.setPasivo(oMarcas.isPasivo());
            this.setNuevoRegistro(false);


        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatosMarca", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void guardar() {
        try {
            MarcasModelos oMarca = new MarcasModelos();
            //Por ser nueva Marca por defecto es activo.
            oMarca.setDescripcion(this.getDescripcionMarca());
            oMarca.setCreadoPor(this.getUsuarioActual().getId());
            oMarca.setCreadoEl(this.getTimeNow());
            oMarca.setCreadoEnIp(this.getRemoteIp());
            oMarca.setPasivo(false);
            oMarca.setPadreId(0);
            oMarcaModeloService.agregar(oMarca);
            mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
            this.setHfId(oMarca.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardarNuevaMarca", MessagesResults.ERROR_GUARDAR, e);

        }
    }

    public void actualizar() {
        try {
            MarcasModelos oMarca = new MarcasModelos();

            oMarca = oMarcaModeloService.obtener(this.getHfId());
            oMarca.setDescripcion(this.getDescripcionMarca());
            oMarca.setPasivo(this.isPasivo());
            oMarca.setModificadoEl(this.getTimeNow());
            oMarca.setModificadoPor(this.getUsuarioActual().getId());
            oMarca.setModificadoEnIp(this.getRemoteIp());
            oMarcaModeloService.actualizar(oMarca);
            mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);

        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizar", MessagesResults.ERROR_MODIFICAR, e);
        }
    }

    public void eliminar() {
        try {
            MarcasModelos oMarca = oMarcaModeloService.obtener(hfId);
            oMarcaModeloService.eliminar(oMarca);
            limpiar();
            buscarMarcaByName();
            mostrarMensajeInfo(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }

    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(MarcasModelos.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }

    }

    public void buscarModeloPorMarca(Integer idMarca) {
        try {
            this.listaModelos = oMarcaModeloService.buscar(this.txtBusquedaMarcaByNombre, "MODELO", idMarca);
            if (this.listaModelos.isEmpty()) {
                mostrarMensajeInfo("No se han encontrado resultados con el criterio de Búsqueda ingresada.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "buscarModeloPorMarca", MessagesResults.ERROR_OBTENER_LISTA, e);
        }

    }

    public void guardarModelos() {
        try {
            MarcasModelos oModelo = new MarcasModelos();
            //Por ser nueva Marca por defecto es activo.
            oModelo.setDescripcion(this.getDescripcionModelo());
            oModelo.setCreadoPor(this.getUsuarioActual().getId());
            oModelo.setCreadoEl(this.getTimeNow());
            oModelo.setCreadoEnIp(this.getRemoteIp());
            oModelo.setPasivo(false);
            oModelo.setPadreId(hfId);
            oMarcaModeloService.agregar(oModelo);
            mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
            this.setHfIdModelo(oModelo.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardarNuevaModelos", MessagesResults.ERROR_GUARDAR, e);

        }
    }

    public void limpiarModelo() {
        this.setNuevoRegistroModelo(true);
        this.setHfIdModelo(null);
        this.setDescripcionModelo("");
        this.setPasivoModelo(false);
        this.setModeloSeleccionada(null);
        this.setTituloDialog("Agregar Marca");
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
            this.cargarModelo(this.getModeloSeleccionada().getId());
            this.setTituloDialog("Editar Modelo");
        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }


    public void cargarModelo(Integer modeloId){
         try {
            MarcasModelos oModelo = oMarcaModeloService.obtener(modeloId);
            this.setDescripcionModelo(oModelo.getDescripcion());
            this.setHfIdModelo(oModelo.getId());
            this.setPasivoModelo(oModelo.isPasivo());
            this.setNuevoRegistroModelo(false);


        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatosModelo", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void guardarOrActualizarModelo() {
        if (this.getHfIdModelo() == null) {
            this.guardarModelo();
        } else {
            this.actualizarModelo();
        }
        this.buscarModeloPorMarca(this.getHfId());
    }

    public void actualizarModelo() {
        try {
            MarcasModelos oModelo = new MarcasModelos();

            oModelo = oMarcaModeloService.obtener(this.getHfIdModelo());
            oModelo.setDescripcion(this.getDescripcionModelo());
            oModelo.setPasivo(this.isPasivo());
            oModelo.setModificadoEl(this.getTimeNow());
            oModelo.setModificadoPor(this.getUsuarioActual().getId());
            oModelo.setModificadoEnIp(this.getRemoteIp());
            oMarcaModeloService.actualizar(oModelo);
            mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);

        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizarModelo", MessagesResults.ERROR_MODIFICAR, e);
        }
    }


    public String getTxtBusquedaMarcaByNombre() {
        return txtBusquedaMarcaByNombre;
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

    public Integer getHfId() {
        return hfId;
    }

    public void setHfId(Integer hfId) {
        this.hfId = hfId;
    }

    public boolean isPasivo() {
        return pasivo;
    }

    public void setPasivo(boolean pasivo) {
        this.pasivo = pasivo;
    }

    public String getDescripcionMarca() {
        return descripcionMarca;
    }

    public void setDescripcionMarca(String descripcionMarca) {
        this.descripcionMarca = descripcionMarca;
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

    public boolean isBtnElminiarVisible() {
        return btnElminiarVisible;
    }

    public void setBtnElminiarVisible(boolean btnElminiarVisible) {
        this.btnElminiarVisible = btnElminiarVisible;
    }

    public boolean isBtnAuditoriaVisible() {
        return btnAuditoriaVisible;
    }

    public void setBtnAuditoriaVisible(boolean btnAuditoriaVisible) {
        this.btnAuditoriaVisible = btnAuditoriaVisible;
    }

    public boolean isNuevoRegistroModelo() {
        return nuevoRegistroModelo;
    }

    public void setNuevoRegistroModelo(boolean nuevoRegistroModelo) {
        this.nuevoRegistroModelo = nuevoRegistroModelo;
    }

    public Integer getHfIdModelo() {
        return hfIdModelo;
    }

    public void setHfIdModelo(Integer hfIdModelo) {
        this.hfIdModelo = hfIdModelo;
    }

    public boolean isPasivoModelo() {
        return pasivoModelo;
    }

    public void setPasivoModelo(boolean pasivoModelo) {
        this.pasivoModelo = pasivoModelo;
    }

    public String getDescripcionModelo() {
        return descripcionModelo;
    }

    public void setDescripcionModelo(String descripcionModelo) {
        this.descripcionModelo = descripcionModelo;
    }

    public MarcasModelos getModeloSeleccionada() {
        return modeloSeleccionada;
    }

    public void setModeloSeleccionada(MarcasModelos modeloSeleccionada) {
        this.modeloSeleccionada = modeloSeleccionada;
    }

    public List getListaAuditoria() {
        return listaAuditoria;
    }

    public void setListaAuditoria(List listaAuditoria) {
        this.listaAuditoria = listaAuditoria;
    }

    public String getTituloDialog() {
        return tituloDialog;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }
}
