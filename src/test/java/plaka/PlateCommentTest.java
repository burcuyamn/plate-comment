package plaka;

import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasStatusCode;
import java.util.ArrayList;
import java.util.List;
import com.yorum.plaka.dao.pojo.NewPlateComment;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.yorum.plaka.dao.PlateDao;
import com.yorum.plaka.PlateUtils;
import com.yorum.plaka.dao.CommentDao;
import com.yorum.plaka.dao.pojo.Plate;
import com.yorum.plaka.resources.PlateCommentResource;
import io.dropwizard.testing.junit.ResourceTestRule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlateCommentTest {

	private static final PlateDao plateDao = mock(PlateDao.class);
	private static final CommentDao commentDao = mock(CommentDao.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().
													addResource(new PlateCommentResource(plateDao, commentDao)).build();
	
	/**
	 * GET metodunu test etmeyi amaçlar.
	 * {@link Plate} tipinde liste oluşturulur ve 1 kayıt eklenir;
	 * sonrasında {@link PlateCommentResource#allList()} metodundan o listenin dönmesi beklenir.
	 * Dönen response 200 ise ve listenin elememan sayısı 1 ise test geçer.
	 */
	@Test
	public void test1_listPlaka() {
		List<Plate> plakaList = new ArrayList<Plate>();
		plakaList.add(new Plate(1,"plate"));
		
		when(plateDao.allList()).thenReturn(plakaList);
		
		Response response= resources.getJerseyTest().target("/plaka").request().get();
		assertThat(response,hasStatusCode(200));
		assertEquals(1,response.readEntity(List.class).size());
	}
	
	/**
	 * GET metodunu test eder. Metod, parametre olarak plaka almaktadır.
	 * Gelen plakanın yorumları listelenebiliyorsa response 200 beklenir.
	 * Response 200 dönüyor ise test başarılıdır.
	 */
	@Test
	public void test2_findCommentByPlate() {
		Response response=resources.getJerseyTest().target("/plaka").path("54RR333").request().get();
		assertThat(response,hasStatusCode(200));
	}
	
	/**
	 * GET metodunu test eder. Metod, parametre olarak plaka almaktadır.
	 * Gelen plakanın yorumları listelenemiyorsa response 400 beklenir.
	 * Response 400 dönüyor ise test başarılıdır.
	 */
	@Test
	public void test3_notFindCommentByFalsePlate() {
		Response response=resources.getJerseyTest().target("/plaka").path("34SA34").request().get();
		assertThat(response,hasStatusCode(400));
	}
	
	/**
	 * POST metodunu test eder.
	 * {@link NewPlateComment} tipinde gelen entitynin kaydedilmesi amaçlanır.
	 * Kayıt gerçekleşirse ve response 201 dönerse test başarılıdır.
	 */
	@Test
	public void test4_savePlateComment() {
		Response response=resources.getJerseyTest().target("/plaka")
				          .request().post(Entity.entity(new NewPlateComment("34TTT456","comment"), MediaType.APPLICATION_JSON));
		assertThat(response,hasStatusCode(201));
	}
	
	/**
	 * {@link PlateUtils#validate(String)} metodunun test edilmesi amaçlanır.
	 * Gönderilen plakalar sonucunda metoddan true dönmesi beklenir.
	 * true dönüyor ise test başarılıdır.
	 */
	@Test
	public void test5_truePlateControl1() {
		String plate="34A2344";
		assertTrue(PlateUtils.validate(plate));
		plate="34B12345";
		assertTrue(PlateUtils.validate(plate));
		plate="34AB123";
		assertTrue(PlateUtils.validate(plate));
		plate="34AB1234";
		assertTrue(PlateUtils.validate(plate));
		plate="34ABC12";
		assertTrue(PlateUtils.validate(plate));
		plate="34ABC123";
		assertTrue(PlateUtils.validate(plate));
	}

	/**
	 * {@link PlateUtils#validate(String)} metodunun test edilmesi amaçlanır.
	 * Gönderilen plakalar sonucunda metoddan false dönmesi beklenir.
	 * false dönüyor ise test başarılıdır.
	 */
	@Test
	public void test6_falsePlateControl1() {
		String plate="5A5";
		assertFalse(PlateUtils.validate(plate));
		plate="05A5";
		assertFalse(PlateUtils.validate(plate));
		plate="05A57";
		assertFalse(PlateUtils.validate(plate));
		plate="05A575";
		assertFalse(PlateUtils.validate(plate));
		plate="57A5";
		assertFalse(PlateUtils.validate(plate));
		plate="57A57";
		assertFalse(PlateUtils.validate(plate));
		plate="57AB57";
		assertFalse(PlateUtils.validate(plate));
		plate="57ABC5";
		assertFalse(PlateUtils.validate(plate));
		plate="57ABC5757";
		assertFalse(PlateUtils.validate(plate));
		plate="84AB567";
		assertFalse(PlateUtils.validate(plate));
		plate="57QX575";
		assertFalse(PlateUtils.validate(plate));
	}
}