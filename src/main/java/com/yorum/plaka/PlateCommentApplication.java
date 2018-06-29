package com.yorum.plaka;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.yorum.plaka.dao.PlateDao;
import com.yorum.plaka.dao.CommentDao;
import com.yorum.plaka.dao.pojo.Plate;
import com.yorum.plaka.dao.pojo.Comment;
import com.yorum.plaka.resources.PlateCommentResource;

import io.dropwizard.Application;

public class PlateCommentApplication extends Application<PlateCommentConfiguration> {

	private final HibernateBundle<PlateCommentConfiguration> hibernate = new HibernateBundle<PlateCommentConfiguration>(
			Plate.class, Comment.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(PlateCommentConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	public static void main(String[] args) throws Exception {
		new PlateCommentApplication().run(args);
	}

	@Override
	public String getName() {
		return "plaka-yorum";
	}

	@Override
	public void initialize(Bootstrap<PlateCommentConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(PlateCommentConfiguration configuration, Environment environment) throws ClassNotFoundException {

		final PlateDao plateDao = new PlateDao(hibernate.getSessionFactory());
		final CommentDao commentDao = new CommentDao(hibernate.getSessionFactory());
		environment.jersey().register(new PlateCommentResource(plateDao, commentDao));
	}
}