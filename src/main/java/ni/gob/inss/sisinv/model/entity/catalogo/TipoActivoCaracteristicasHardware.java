package ni.gob.inss.sisinv.model.entity.catalogo;
// Generated 11-29-2016 09:04:36 AM by Hibernate Tools 5.1.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;

/**
 * TipoActivoCaracteristicasHardware generated by hbm2java
 */
@Entity
@Table(name = "tipo_activo_caracteristicas_hardware", schema = "catalogo", uniqueConstraints = @UniqueConstraint(columnNames = {
		"tipo_activo_id", "caracteristica_padre_id" }))
@SequenceGenerator(name="SerialTipoActivoCaracteristicasHardware", allocationSize=1, schema="catalogo", sequenceName="catalogo.tipo_activo_caracteristicas_hardware_id_seq")
public class TipoActivoCaracteristicasHardware extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer tipoActivoId;
	private Catalogo oTipoActivo;
	private Integer caracteristicaPadreId;
	private CaracteristicasHardware oCaracteristicaPadre;
	private Boolean pasivo;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SerialTipoActivoCaracteristicasHardware")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tipo_activo_id", nullable = false)
	public Integer getTipoActivoId() {
		return this.tipoActivoId;
	}

	public void setTipoActivoId(Integer tipoActivoId) {
		this.tipoActivoId = tipoActivoId;
	}

	@Column(name = "caracteristica_padre_id", nullable = false)
	public Integer getCaracteristicaPadreId() {
		return this.caracteristicaPadreId;
	}

	public void setCaracteristicaPadreId(Integer caracteristicaPadreId) {
		this.caracteristicaPadreId = caracteristicaPadreId;
	}

	@Column(name = "pasivo")
	public Boolean getPasivo() {
		return this.pasivo;
	}

	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_activo_id", insertable=false, updatable=false)
	public Catalogo getoTipoActivo() {
		return oTipoActivo;
	}

	public void setoTipoActivo(Catalogo oTipoActivo) {
		this.oTipoActivo = oTipoActivo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="caracteristica_padre_id", insertable=false, updatable=false)
	public CaracteristicasHardware getoCaracteristicaPadre() {
		return oCaracteristicaPadre;
	}

	public void setoCaracteristicaPadre(CaracteristicasHardware oCaracteristicaPadre) {
		this.oCaracteristicaPadre = oCaracteristicaPadre;
	}

}
