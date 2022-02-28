//package ru.sfedu.test.model.beans;
//
//import org.codehaus.plexus.util.StringUtils;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import ru.sfedu.test.Constants;
//
//import java.io.IOException;
//import java.util.Objects;
//
//public class FilmTest {
//    private Film film;
//    private Film film1;
//    private Film film2;
//    private Film film3;
//
//    @Before
//    public void setUp() throws Exception {
//        film = new Film("", 1);
//        film1 = new Film("It", 2017);
//        film2 = new Film("It 2", 2019);
//        film3 = new Film("It", 2017);
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void testSetNamePositive() throws IOException {
//        film.setName("Harry Potter");
//        Assert.assertEquals("Harry Potter", film.getName());
//    }
//
//    @Test
//    public void testSetNameNegative() throws IOException {
//        film.setName("_");
//        Assert.assertNotSame("Harry Potter", film.getName());
//    }
//
//    @Test
//    public void testSetNameExceptionMessage() {
//        try {
//            film.setName(StringUtils.repeat("_", 256));
//        } catch (Exception e) {
//            Assert.assertEquals(Constants.FILM_INVALID_NAME, e.getMessage());
//        }
//    }
//
//    @Test(expected = IOException.class)
//    public void testSetNameExceptionClass() throws IOException {
//        film.setName(StringUtils.repeat("_", 256));
//    }
//
//    @Test
//    public void testSetYearPositive() throws IOException {
//        film.setYear(2006);
//        Assert.assertEquals(2006, film.getYear());
//    }
//
//    @Test
//    public void testSetYearNegative() throws IOException {
//        film.setYear(2006);
//        Assert.assertNotSame(1, film.getYear());
//    }
//
//    @Test
//    public void testSetYearExceptionMessage() {
//        try {
//            film.setYear(0);
//        } catch (Exception e) {
//            Assert.assertEquals(Constants.FILM_INVALID_YEAR, e.getMessage());
//        }
//    }
//
//    @Test(expected = IOException.class)
//    public void testSetYearExceptionClass() throws IOException {
//        film.setYear(999999);
//    }
//
//    @Test
//    public void testTestGetNamePositive() {
//        Assert.assertEquals("It", film1.getName());
//    }
//
//    @Test
//    public void testTestGetNameNegative() {
//        Assert.assertNotSame("Its", film1.getName());
//    }
//
//    @Test
//    public void testGetYearPositive() {
//        Assert.assertEquals(2017, film1.getYear());
//    }
//
//    @Test
//    public void testGetYearNegative() {
//        Assert.assertNotSame(1, film1.getYear());
//    }
//
//    @Test
//    public void testTestToStringPositive() {
//        Assert.assertEquals("Film{id=" + film1.getId() + ", name='It', year=2017}", film1.toString());
//    }
//
//    @Test
//    public void testTestToStringNegative() {
//        Assert.assertNotSame(" ", film1.toString());
//    }
//
//    @Test
//    public void testTestEqualsPositive() {
//        Assert.assertTrue(film1.equals(film3));
//    }
//
//    @Test
//    public void testTestEqualsNegative() {
//        Assert.assertFalse(film1.equals(film2));
//    }
//
//    @Test
//    public void testTestHashCode() {
//        Assert.assertEquals(Objects.hash(film1.getId(), film1.getName(), film1.getYear()), film1.hashCode());
//    }
//}