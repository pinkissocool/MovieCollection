import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsRating(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            double tempRating = temp.getUserRating();


            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating > listToSort.get(possibleIndex - 1).getUserRating())
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsRevenue(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            double tempRevenue = temp.getRevenue();


            int possibleIndex = j;
            while (possibleIndex > 0 && tempRevenue > listToSort.get(possibleIndex - 1).getRevenue())
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsString(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);
            String tempTitle = temp;

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a person: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        ArrayList<String> people = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++){

            String cast = movies.get(i).getCast().toLowerCase();
            ArrayList<String> castList = new ArrayList<String>();


            int numDelim = 0;
            for (int c = 0; c < cast.length(); c++){
                if (cast.substring(c, c+1).equals("|")){
                    numDelim++;
                }
            }

            while (numDelim >0){
                int endIdx = cast.indexOf("|");
                castList.add(cast.substring(0, endIdx));
                cast = cast.substring(endIdx+1);
                numDelim--;
            }
            castList.add(cast);


            for (int k = 0; k < castList.size(); k++){
                String person = castList.get(k);
                if (people.indexOf(person) == -1 && person.contains(name) && !person.contains("\\|")){
                    people.add(person);
                }
            }
        }
        sortResultsString(people);
        for (int j = 0; j < people.size(); j++){
            System.out.println(j + 1 + ". " + people.get(j));
        }

        System.out.print("Which actor do you want to learn more about? Put in the number: ");
        int answer = scanner.nextInt();
        ArrayList<Movie> actorMovies = new ArrayList<Movie>();

        String actor = people.get(answer-1);
        for (int i = 0; i < movies.size(); i++){
            String moviesCast = movies.get(i).getCast().toLowerCase();
            if (moviesCast.indexOf(actor) != -1){
                actorMovies.add(movies.get(i));
            }
        }

        sortResults(actorMovies);

        for (int i = 0; i < actorMovies.size(); i++)
        {
            String title = actorMovies.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = actorMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        /* TASK 3: IMPLEMENT ME! */
        System.out.print("Enter a keyword: ");
        String keyword = scanner.nextLine();
        keyword = keyword.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();



        for (int i = 0; i < movies.size(); i++){
            String movieKeys = movies.get(i).getKeywords();
            movieKeys = movieKeys.toLowerCase();
            if (movieKeys.indexOf(keyword) != -1){
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {

    }

    private void listHighestRated()
    {
        ArrayList<Movie> temp = movies;
        ArrayList<Movie> highestRated = new ArrayList<>();
        sortResultsRating(temp);
        for (int i = 0; i < 50; i++)
        {
            highestRated.add(0, temp.get(i));
            System.out.println(i + 1 + ". " + temp.get(i).getTitle() + ": " + temp.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRated.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temp = movies;
        ArrayList<Movie> highestRevenue = new ArrayList<>();
        sortResultsRevenue(temp);
        for (int i = 0; i < 50; i++)
        {
            highestRevenue.add(temp.get(i));
            System.out.println(i + 1 + ". " + temp.get(i).getTitle() + ": " + temp.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRevenue.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                // import all cells for a single row as an array of Strings,
                // then convert to ints as needed
                String[] movieFromCSV = line.split(",");

                // pull out the data for this cereal
                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                // create Cereal object to store values
                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

                // adding Cereal object to the arraylist
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }

    }

    // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}