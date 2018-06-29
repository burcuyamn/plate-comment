package com.yorum.plaka.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.yorum.plaka.dao.pojo.Plate;
import com.yorum.plaka.dao.pojo.Comment;
import io.dropwizard.hibernate.AbstractDAO;

public class CommentDao extends AbstractDAO<Comment> {

	public CommentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
	 *Gelen plakayi ve yorumu veritabanindaki 'Comment' tablosuna kaydeder.
	 *@param plate int plaka id ve string tipte plakay, icerir.
	 *@param comment string tiptedir ve plakaya yapilan yorumu icerir.
	 */
	public void saveComment(Plate plate,String comment) {
		Comment newComment = new Comment();
		newComment.setPlate(plate);
		newComment.setComment(comment);
		currentSession().save(newComment);
	}
	
	/**
	 * Parametre olarak gelen plakaya kayitli plaka yorumlarini listeler.
	 * Plaka ve yorum tablosu plakaId ile foreign key baglantiya sahiptir, criteria da buna uygun olarak yazilmistir.
	 * @param plate string olarak plakayi icerir.
	 * @return {@link Comment} tipinde liste dondurur.
	 */
	public List<Comment> findCommentByPlate(String plate) {
		Criteria commentCriteria = currentSession().createCriteria(Comment.class).createAlias("plate", "p")
				.add(Restrictions.eq("p.plate", plate));

		ProjectionList properties = Projections.projectionList();
		properties.add(Projections.property("comment"));
		commentCriteria.setProjection(properties);
		return list(commentCriteria);
	}
}