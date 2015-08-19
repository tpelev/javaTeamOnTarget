package model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name="categories")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id"),
    @NamedQuery(name = "Category.findByCategorieName", query = "SELECT c FROM Category c WHERE c.categorieName = :categorieName")
})
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="CATEGORIE_NAME")
	private String categorieName;

	//bi-directional many-to-one association to Advertisement
	@OneToMany(mappedBy="category", fetch=FetchType.EAGER)
	private List<Advertisement> advertisements;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategorieName() {
		return this.categorieName;
	}

	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}

	public List<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(List<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public Advertisement addAdvertisement(Advertisement advertisement) {
		getAdvertisements().add(advertisement);
		advertisement.setCategory(this);

		return advertisement;
	}

	public Advertisement removeAdvertisement(Advertisement advertisement) {
		getAdvertisements().remove(advertisement);
		advertisement.setCategory(null);

		return advertisement;
	}

}