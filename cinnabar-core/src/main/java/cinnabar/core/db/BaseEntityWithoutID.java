package cinnabar.core.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BaseEntityWithoutID {
	
	private Long id;
	
	private String cb;
	
	private Date cd;
	
	private String mb;
	
	private Date md;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, name="created_by")
	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	@Column(nullable=false, name="created_date")
	public Date getCd() {
		return cd;
	}

	public void setCd(Date cd) {
		if (cd == null) {
			cd = new Date();
		}
		this.cd = cd;
	}

	@Column(nullable=false, name="modified_by")
	public String getMb() {
		return mb;
	}

	public void setMb(String mb) {
		this.mb = mb;
	}

	@Column(nullable=false, name="modified_date")
	public Date getMd() {
		return md;
	}

	public void setMd(Date md) {
		if (md == null) {
			md = new Date();
		}
		this.md = md;
	}
	
}
