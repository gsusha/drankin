package ru.sfedu.Test.model.beans;

import junit.framework.TestCase;
import org.codehaus.plexus.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

public class FilmTest extends TestCase {
    private Film filmGS;
    private Film film1;
    private Film film2;
    private Film film3;

    public void setUp() throws Exception {
        super.setUp();
        filmGS = new Film("", 1);
        film1 = new Film("It", 2017);
        film2 = new Film("It 2", 2019);
        film3 = new Film("It", 2017);
    }

    public void tearDown() throws Exception {
    }

    public void testSetNamePositive() throws IOException {
        filmGS.setName("Harry Potter");
        assertEquals("Harry Potter", filmGS.getName());
    }

    public void testSetNameNegative() throws IOException {
        filmGS.setName("1");
        assertNotSame("Harry Potter", filmGS.getName());
    }

    public void testSetNameException() throws IOException {
        try {
            filmGS.setName(StringUtils.repeat("*", 256));
        } catch (Exception e) {
            assertEquals("Invalid name", e.getMessage());
        }
    }

    public void testSetYearPositive() throws IOException {
        filmGS.setYear(2006);
        assertEquals(2006, filmGS.getYear());
    }

    public void testSetYearNegative() throws IOException {
        filmGS.setYear(2006);
        assertNotSame(1, filmGS.getYear());
        assertNotSame(2, filmGS.getYear());
    }

    public void testSetYearException() throws IOException {
        try {
            filmGS.setYear(0);
        } catch (Exception e) {
            assertEquals("Invalid year", e.getMessage());
        }
    }

    public void testTestGetNamePositive() {
        assertEquals( "It", film1.getName());
    }

    public void testTestGetNameNegative() {
        assertNotSame("Its", film1.getName());
    }

    public void testGetYearPositive() {
        assertEquals(2017, film1.getYear());
    }

    public void testGetYearNegative() {
        assertNotSame(1, film1.getYear());
    }

    public void testTestToStringPositive() {
        assertEquals("Film{id=" + film1.getId() + ", name='It', year=2017}", film1.toString());
    }

    public void testTestToStringNegative() {
        assertNotSame(" ", film1.toString());
    }

    public void testTestEqualsPositive() {
        assertTrue(film1.equals(film3));
    }

    public void testTestEqualsNegative() {
        assertFalse(film1.equals(film2));
    }

    public void testTestHashCode() {
        assertEquals(Objects.hash(film1.getId(), film1.getName(), film1.getYear()), film1.hashCode());
    }
}