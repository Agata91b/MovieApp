package agata91bcomgithub.movieapp.details;



import com.annimon.stream.Objects;
import com.google.gson.annotations.SerializedName;




public class MovieDetails  {

    @SerializedName("Title")
    private  String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Awards")
    private String awards;

    @SerializedName("Poster")
    private String poster;

    private String imdbRating;

    @SerializedName("Type")
    private String type;

    @SerializedName("Response")
    private String response;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDetails that = (MovieDetails) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(year, that.year) &&
                Objects.equals(rated, that.rated) &&
                Objects.equals(runtime, that.runtime) &&
                Objects.equals(director, that.director) &&
                Objects.equals(actors, that.actors) &&
                Objects.equals(plot, that.plot) &&
                Objects.equals(awards, that.awards) &&
                Objects.equals(poster, that.poster) &&
                Objects.equals(imdbRating, that.imdbRating) &&
                Objects.equals(type, that.type) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, rated, runtime, director, actors, plot, awards, poster, imdbRating, type, response);
    }
}
