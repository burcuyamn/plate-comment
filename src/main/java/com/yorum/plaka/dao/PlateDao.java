package com.yorum.plaka.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import com.yorum.plaka.dao.pojo.Plate;
import io.dropwizard.hibernate.AbstractDAO;
import com.yorum.plaka.dao.pojo.NewPlateComment;

public class PlateDao extends AbstractDAO<Plate> {

	CommentDao commentDao;

	public PlateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Veritabaninda kayitli tum plaka kayitlarini listeler
	 * @return {@link Plate} tipte liste dondurur.
	 */
	public List<Plate> allList() {
		return list(criteria());
	}

	/**
	 * Gelen parametredeki plakayi findPlate metoduna yollar,
	 * findPlate metodu parametre icinde gelen veriyi veritabaninda arar, plate tipinde veri dondurur.
	 * findPlate metodundan null donerse; NewPlateComment icinde gelen plaka ile yeni plaka kaydi olusturulur ve veritabanina kaydedilir.
	 * findPlate metodundan plaka donerse; donen plaka savePlateAndComment metodundan da doner.
	 * @param newPlateComment string tipte plaka ve yorumdan olusur.
	 * @return {@link Plate} tipinde deger dondurur.
	 */
	public Plate savePlateAndComment(NewPlateComment newPlateComment) {
		Plate plate = findPlate(newPlateComment.getPlate());
		
		if (plate == null) {
			Plate newPlate = new Plate();
			newPlate.setPlate(newPlateComment.getPlate());
			newPlate = persist(newPlate);

			return newPlate;
		}
		return plate;
	}

	/**
	 * Gelen string plaka verisine gore ilgili kaydi veritabanindan getirir.
	 * @param plate string tiptedir.
	 * @return tek sonuc veya null dondurur.
	 */
	public Plate findPlate(String plate) {
		Criteria criteria = currentSession().createCriteria(Plate.class);
		criteria.add(Restrictions.eq("plate", plate));
		return uniqueResult(criteria);
	}
}