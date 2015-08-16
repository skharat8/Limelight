package com.centerstage.limelight.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.centerstage.limelight.model.Genre;
import com.centerstage.limelight.model.LimelightDbHelper;
import com.centerstage.limelight.model.LimelightMovie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Smitesh on 8/15/2015.
 */
public class TestDb extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        mContext.deleteDatabase(LimelightDbHelper.DATABASE_NAME);
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add("LimelightMovie");

        SQLiteDatabase db = new LimelightDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain the LimelightMovie table
        assertTrue("Error: Your database was created without the LimelightMovie table",
                tableNameHashSet.isEmpty());

        c.close();
        db.close();
    }

    public void testMovieTable() {
        SQLiteDatabase db = new LimelightDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // Insert values into the movie table
        LimelightMovie testMovie = new LimelightMovie();
        testMovie.setMovieId(10);
        testMovie.setMovieTitle("MovieTester");
        testMovie.setUserRating(7.0);

        // Insert a genre value into the movie table
        List<Genre> testGenres = new ArrayList<>();
        Genre testGenre = new Genre();
        testGenre.setId(11);
        testGenre.setName("Action");
        testGenres.add(testGenre);
        testMovie.setGenres(testGenres);

        long rowId = cupboard().withDatabase(db).put(testMovie);

        // Verify we got a row back
        assertTrue(rowId != -1);

        // Query the database
        LimelightMovie queriedMovie = cupboard().withDatabase(db).query(LimelightMovie.class).get();

        TestUtilities.validateMovie(testMovie, queriedMovie);

        db.close();
    }
}
