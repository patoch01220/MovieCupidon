package ch.unige.domain;

public class LobbyConfig {

	private String[] genreList;
    private int[] rangeYear;

    public LobbyConfig(){}

    public String[] getGenreList() {
        return genreList;
    }

    public void setGenreList(String[] genreList) {
        this.genreList = genreList;
    }

    public int[] getRangeYear() {
        return rangeYear;
    }

    public void setRangeYear(int[] rangeYear) {
        this.rangeYear = rangeYear;
    }

}
