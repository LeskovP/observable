package sample;

public class CountryCapital {

    private String country;

    private String capital;

    public CountryCapital(String country, String capital) {
        this.country = country;
        this.capital = capital;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CountryCapital) {
            CountryCapital s = (CountryCapital)obj;
            return country.equals(s.country) && capital.equals(s.capital);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return country.hashCode() * 31 + capital.hashCode();
    }



}
