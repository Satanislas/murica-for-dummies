package com.example.murica_for_dummies;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.murica_for_dummies.Misc.MyAdapter;
import com.example.murica_for_dummies.Misc.ThemeSelector;
import com.example.murica_for_dummies.Utils.DistanceConverters;
import com.example.murica_for_dummies.Utils.MassConverters;
import com.example.murica_for_dummies.Utils.VolumeConverters;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ConverterTestMass(){
        double kg = 1;
        assertEquals(kg, MassConverters.poundToKilograms(MassConverters.kilogramToPounds(kg)));
        assertNotEquals(kg,MassConverters.kilogramToPounds(kg));
    }

    @Test
    public void ConverterTestVolume(){
        double L = 1;
        assertEquals(L, VolumeConverters.gillToLiters(VolumeConverters.litersToGill(L)));
        assertNotEquals(L,VolumeConverters.litersToGill(L));
    }

    @Test
    public void ConverterTestDistance(){
        double m = 1000;
        assertEquals(m, DistanceConverters.milesToMeter(DistanceConverters.meterToMiles(m)));
        assertNotEquals(m,DistanceConverters.meterToMiles(m));
    }

    @Test
    public void ThemeSetTests(){
        int ThemeID = 12345;

        assertNotEquals(ThemeID,ThemeSelector.getSelectedTheme());
        ThemeSelector.setCurrentSelectedTheme(ThemeID);
        assertEquals(ThemeID,ThemeSelector.getSelectedTheme());

        ThemeID = 54321;
        assertNotEquals(ThemeID,ThemeSelector.getSelectedTheme());
        ThemeSelector.setCurrentSelectedTheme(ThemeID);
        assertEquals(ThemeID,ThemeSelector.getSelectedTheme());
    }

    @Test
    public void AdapterTests(){
        List<String> stringList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(stringList,null);
        assertEquals(0,adapter.getItemCount());

        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.add("4");
        stringList.add("5");
        stringList.add("6");
        assertEquals(6,adapter.getItemCount());
    }
}