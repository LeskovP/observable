package sample;

import java.util.Comparator;

public class CountryCapitalComparator implements Comparator<CountryCapital> {

    @Override
    public int compare(CountryCapital o1, CountryCapital o2) {
        int result = o1.getCountry().compareTo(o2.getCountry());
        if (result == 0) {
            return o1.getCapital().compareTo(o2.getCapital());
        }
        return result;
    }
}
