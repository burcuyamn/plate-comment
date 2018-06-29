package com.yorum.plaka.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Strings;
import com.yorum.plaka.dao.PlateDao;
import com.yorum.plaka.dao.pojo.Comment;
import com.yorum.plaka.dao.pojo.NewPlateComment;
import com.yorum.plaka.dao.CommentDao;
import com.yorum.plaka.dao.pojo.Plate;
import io.dropwizard.hibernate.UnitOfWork;
import com.yorum.plaka.PlateUtils;

@Path("/plaka")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlateCommentResource {

	PlateDao plateDao;
	CommentDao commentDao;

	public PlateCommentResource(PlateDao plateDao, CommentDao commentDao) {
		this.plateDao = plateDao;
		this.commentDao = commentDao;
	}

	/**
	 * Get istegii geldiginde {@link PlateDao#allList()} metodunu calistirir.
	 * @return Respose 200 ile birlikte {@link Plate} listesini dondurur.
	 
	/**
	 * @api {get} /plaka Tum plakalar
	 * @apiVersion 0.1.0
	 * @apiName GetPlates
	 * @apiGroup Plate
	 *
	 * @apiSuccess {Number} id Plaka Id.
	 * @apiSuccess {String} plate Plaka.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *   [  
	 *     {
	 *       "id": "1",
	 *       "plate": "34AS772"
	 *     },
	 *     {
	 *       "id": "2",
	 *       "plate": "57AS277"
	 *     }
	 *   ] 
	 */
	@GET
	@UnitOfWork
	public Response allList() {
		return Response.status(200).entity(plateDao.allList()).build();
	}

	/**
	 * Gelen plate i {@link PlateUtils} icersinde bulunan {@link PlateUtils#validate(Plate)} metoduna gonnderir.
	 * {@link PlateUtils#validate(Plate)} metodundan false donerse Response 400 dondurur.
	 * {@link PlateUtils#validate(Plate)} metodundan true donerse; plakayÄ± {@link PlateUtils#standardize(String)}
	 * metoduna gondererek standartlastirir.
	 * Plakayi {@link CommentDao#findCommentByPlate(String)} metoduna gonderir; plakaya ait yorumlarý bulundugu 
	 * {@link Comment} tipinde liste bekler.
	 * Donen liste bos ise Response 200 dondurur.
	 * DÃ¶nen liste doluysa Response 200 ile birlikte listeyi dondurur
	 * @param plate string tipinde plakadir.
	 * @return Response status kod doner.

	/**
	 * @api {get} /plaka/:plate Aranan plakanÄ±n tum yorumlari
	 * @apiVersion 0.1.0
	 * @apiName GetPlateComments
	 * @apiGroup Plate
	 *
	 * @apiParam {String} plate Plaka
	 *
	 * @apiSuccess {String} comments Yorumlar.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     [
	 *       "comment 1" ,
	 *       "comment 2" 
	 *     ]
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 400 Bad Request
	 *     {
	 *       "error": "Plate is not suitable"
	 *     }     
	 *     
	 */
	@GET
	@UnitOfWork
	@Path("/{plate}")
	public Response findCommentByPlate(@PathParam("plate") String plate) {
		if (!PlateUtils.validate(plate)) {
			return Response.status(400).build();
		}
		
		plate = PlateUtils.standardize(plate);
		
		List<Comment> comments = commentDao.findCommentByPlate(plate);
		
		if (comments.isEmpty()) {
			return Response.status(200).build();
		}

		return Response.status(200).entity(comments).build();
	}

	/**
	 * Gelen parametrede bulunan plakayi ve yorumu kaydetmeye yarayan metoddur.
	 * parametre ile gelen yorumun ve plakanin; null veya bos olup olmadigimi kontrol eder.
	 * Ikisi de null veya bos ise Response 400 dondurur; bos degilse {@link PlateUtils#validate(String)} metoduna gonderir.
	 * {@link PlateUtils#validate(String)} metodundan false donerse Response 400 dondurur; true donerse 
	 * {@link PlateUtils#standardize(String)} metoduna gondererek standartlastirir.
	 * Sonrasýnda plakayi {@link PlateDao#savePlateAndComment(NewPlateComment)} metoduna gondererek kaydeder,
	 * yorumu {@link CommentDao#saveComment(Plate, String)} metosuna gondererek kaydeder.
	 * Tum bunlar basarili ise Response 201 dondurur. 
	 * @param newPlateComment string plaka ve string yorum icerir.
	 * @return Response status kod dondurur.
	 
	/**
	 * @api {post} /plaka/:plate Plaka ve yorum kaydet
	 * @apiVersion 0.1.0
	 * @apiName SavePlateComments
	 * @apiGroup Plate
	 *
	 * @apiParam {String} plate Plaka
	 * @apiParam {String} comment Yorum
	 *
	 *
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 201 Created.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 400 Bad Request
	 *     {
	 *       "error": "Plate is not suitable or incomplete request"
	 *     }     
	 *     
	 */
	@POST
	@UnitOfWork
	@Timed
	public Response save(NewPlateComment newPlateComment) {
		if (Strings.isNullOrEmpty(newPlateComment.getComment()) && Strings.isNullOrEmpty(newPlateComment.getPlate())) {
			return Response.status(400).build();
		}

		if (!PlateUtils.validate(newPlateComment.getPlate())) {
			return Response.status(400).build();
		}

		newPlateComment.setPlate(PlateUtils.standardize(newPlateComment.getPlate()));
		
		Plate plate = plateDao.savePlateAndComment(newPlateComment);
		commentDao.saveComment(plate, newPlateComment.getComment());
		
		return Response.status(201).build();
	}
}