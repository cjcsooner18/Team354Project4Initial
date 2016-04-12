
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class Parse extends Driver {
	
	private String file;
	
	public Parse(String fileName) {
		this.file = fileName;
	}
	
	/**
	 * This method contains the specific characters that are arrows. It helps
	 * the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	
	private final static boolean removeArrow (char c) {
		return c == '<' || c == '>';
	}
	
	private final static boolean removeComma(char c) {
		return c == ',';
	}
	
	/**
	 * This method contains the specific characters that are brackets. It helps
	 * the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	private final static boolean removeBracket(char c) {
		return c == '[' || c == ']';
	}
	
	/**
	 * This method contains the specific numbers that are digits. It helps
	 * the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	private final static boolean isDigit(char c){
		return c>= '0' && c <= '9';
	}
	
	/**
	 * This method contains the specific characters that are considered spaces or tabs. 
	 * It helps the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	private final static boolean whiteSpace(char c) {
	 return	c == ' ' || c == '\t';
	}
	
	/**
	 * This method contains the specific characters that are parentheses. It helps
	 * the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	private final static boolean isParentheses(char c) {
		return c == '(' || c == ')';	
	}
	
	/**
	 * This method contains the specific numbers that are digits. It helps
	 * the parser method
	 * @param         Char c    		Contains individual characters.
	 * @return        boolean			Returns true or false
	 */
	private final static boolean isReleaseType(char c) {
		return c == 'T' || c == 'V';
	}
	
	private final static boolean removeCurlyBrace(char c) {
		return c == '{' || c == '}';
	}
	
	private final static boolean removeHastag(char c) {
		return c == '#';
	}
	
	private final static boolean removeQuestionMark(char c) {
		return c == '?';
	}
	private final static boolean removeDashSlashAndPeriods(char c) {
		return c == '-' || c == '.' || c == '/';
	}
	
	private final static boolean letterCheck(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}
	
	
	
	/**
	 * This method is to use a line from a file to create a Movie object. This
	 * method should simultaneously create the Movie object and add it to the
	 * List parameter. It should also check to see if the Media object already
	 * exists in the list before adding it 
	 * 
	 * @param line
	 * @param list
	 */
	public Movies parseMovie(String line) {
		
	
		/** String year
		 *  the year of the movie.
		 *  This will be converted to a Integer below.
		 */
		 String year = "";
		 
		 /** String movieName
		  *  the name of the movie
		  */
		 String movieName = "";
		 
		 /** String movieInfo
		  *  contains additional information
		  *  of the movie
		  */
		 String movieInfo = "";
		 
		 /** String releaseInfo
		  *  contains information on how the movie was released
		  */
		 String releaseInfo = "";
		 
		 /** General Idea: treat the line like a character array.
		  * Make use of the string.charAt(int) method, to retrieve
		  * characters from the string. Keep an index variable to track
		  * where in the array you are.*/
		 
		 int index = line.length()-1;
		 
		 // Parse year
		 while (isDigit(line.charAt(index)))
		 {
			 year = line.charAt(index) + year;
			 index--;
		 }
		 
		 // If no year was parsed, remove any question marks
		 if (year.length() == 0) {
			 while (removeQuestionMark(line.charAt(index))) {
				 index--;
			 }
		 }
		 
		 /*Skip whitespace*/
		 while (whiteSpace(line.charAt(index))) 
		 {
			 index--;
			 
			 if (isParentheses(line.charAt(index))) 
			 {
				 index--; // removes first parentheses
				 
				 while (isReleaseType(line.charAt(index))) 
				 {
					 // stores the release info of each movie object
					 releaseInfo = line.charAt(index) + releaseInfo;
					 index--;
					 
					 if (isParentheses(line.charAt(index))) 
					 {
						 index--; // skips the last parentheses
					 } 
				 }
				 
				 // If there are question marks in parentheses, remove the question marks
				 while (removeQuestionMark(line.charAt(index))) {
					index--;
					
					if (isParentheses(line.charAt(index))) {
						index--;
					}
				 }
				 
				 // this while statement will check for additional movie
				 // information
				 while (isDigit(line.charAt(index))) 
				 {
					movieInfo = line.charAt(index) + movieInfo;
					index--;
							
					if (isParentheses(line.charAt(index))) {
						index--;
					}
				 }
			 }
		 }
		 // the remaining index should contain the title:
		 while (index >= 0)
		 {
			 movieName = line.charAt(index) + movieName;
			 index--;	 
		 }
		
		 Integer yearReleased;
		 // If the line did not contain a year number, parse yearReleased as 0
		 if (year.length() == 0) {
			 yearReleased = new Integer(0);
		 } else {
			yearReleased = Integer.parseInt(year);
		 }
		 
		 Movies newMovie = new Movies(movieName, yearReleased, releaseInfo);
		 return newMovie;
	}

	/**
	 * This method will take a line from a file and create a Series object.This
	 * method should simultaneously create the Series object and add it to the
	 * List parameter . It should also check to see if the Media object already
	 * exists in the list before adding it using
	 * "list.contains(new object here);"
	 * 
	 * @param line
	 * @param list
	 */

	public Series parseSeries(String line) {
			
			String seriesTitle = "";
			String startYear = "";
			String endYear = "";
			String episodeTitle = "";
			char seasonNumber = ' ';
			char episodeNumber = ' ';
			boolean suspended = false; 
			ArrayList<EpisodeTV> episodeList = new ArrayList<EpisodeTV>();
			ArrayList<Series> serieses;
			Integer startyear = new Integer(0);
			Integer endyear = new Integer(0);
			
			// start on the left of the line
			int index = line.length()-1;
			// check for the end date 
			while(isDigit(line.charAt(index))) {
				endYear = line.charAt(index) + endYear;
				--index;
			}
			// if end date does not exist remove questions
			while(removeQuestionMark(line.charAt(index))) {
				--index;
			}
			// if end date did exist remove dash between dates	
			if (removeDashSlashAndPeriods(line.charAt(index))) {
				--index;
			}
			// if dash was removed, add the next digits to the start date
			while (isDigit(line.charAt(index))) 
			{
				startYear = line.charAt(index) + startYear;
				--index;
			}
			// remove white space
			while (whiteSpace(line.charAt(index))) 
			{
				--index;
				//remove first curly brace
				if (removeCurlyBrace(line.charAt(index))) 
				{
					--index;
					//remove first parentheses
					if(isParentheses(line.charAt(index))) 
					{
						--index;
						// if we have reached this step, information containing
						// the episode number should be located here
						if (isDigit(line.charAt(index)))
						{
							episodeNumber = line.charAt(index);
							// remove the period that separates the season and episode
							// numbers
							if (removeDashSlashAndPeriods(line.charAt(index))) 
							{
								--index;
								// if we have reached this point, this information will contain 
								// the season number of the current episode
								if (isDigit(line.charAt(index))) 
								{
									seasonNumber = line.charAt(index);
									// that annoying hash tag should be present if we have reached this stage
									if (removeHastag(line.charAt(index))) 
									{
										--index;
										// the last parentheses should be present if we make it to this step.
										if (isParentheses(line.charAt(index))) 
										{
											index--;
										}
									}
								}
							}
						}
					}
					// checking to see if the episode or season is suspended
					else if (removeCurlyBrace(line.charAt(index))) 
					{
						--index;
						// this allows the parser to move through the line if it contains {{SUSPENDED}}
						while(!removeCurlyBrace(line.charAt(index))) 
						{
							--index;
							suspended = true;
						}
						//this 
						if(removeCurlyBrace(line.charAt(index))) 
						{
							--index;
							if(removeCurlyBrace(line.charAt(index))) 
							{
								--index;
							}
						}
						
					}
					// this keep moving through the index, until it reaches the last curly brace
					// this will include the episode title.
					while (!removeCurlyBrace(line.charAt(index)))
					{
						episodeTitle = line.charAt(index) + episodeTitle;
						--index;
					}
					// remove the first parentheses that contains the start year of the series
					if (isParentheses(line.charAt(index))) 
					{
						--index;	
					}
						// this while loop will go thru and assign the characters to the startYear string
						while (isDigit(line.charAt(index))) 
						{
							startYear = line.charAt(index) + startYear;
							--index;
							// this will remove the last parentheses from the line
							if (isParentheses(line.charAt(index))) 
							{
								--index;
							}
							
						}	
						
				}
				if (isParentheses(line.charAt(index))) {
					--index;
					while (isDigit(line.charAt(index))) {
						startYear = "";
						startYear = line.charAt(index) + startYear;
						--index;
					}
				}
		}
			// all the white space should be removed. We should be left with 
			// a string that contains the series name surrounded by quotations
			
			// the last character of the remaining string should always contain a
			// quotation mark. We do not need this.
			--index;
			// at index == 0, the first character is always a quotation mark, and we do not want those.
			while (index > 0) {
				seriesTitle = line.charAt(index) + seriesTitle;
				--index;
			}
			
			
			//convert the strings into Integer
			if ((startYear.length() > 0 && endYear.length() > 0)) {
			startyear = Integer.parseInt(startYear);
			endyear = Integer.parseInt(endYear);
			}
			
			// check condition if we need to create an EpisodeTV object or an SeriesTV object
			// if episode title exists, we create an episodeTV object
		if (!episodeTitle.isEmpty()) {
				//another condition, some episode Titles do not contain season and episode numbers
				if(isDigit(episodeNumber) && isDigit(seasonNumber)) {
					int episodeNum = Character.getNumericValue(episodeNumber);
					int seasonNum = Character.getNumericValue(seasonNumber);
					EpisodeTV episode = new EpisodeTV(episodeTitle, endyear, seriesTitle, seasonNum, episodeNum, startyear, suspended);
					
					//method to find series in list? if it is add Episode to 
					
					
					episodeList.add(episode);
					Series series = new Series(seriesTitle, startyear, endyear, episodeList);
					return series;
				}
				else 
				{
					EpisodeTV episode = new EpisodeTV(episodeTitle, startyear, seriesTitle, suspended);
					
					//method to find series in list? If series does not exist create new episodeList for series
					
					episodeList.add(episode);
					Series series = new Series(seriesTitle, startyear, endyear, episodeList);
					return series;
					
				}
			}
		
		Series series = new Series(seriesTitle, startyear, endyear);
		return series;
		
	}

	/**
	 * This method will take a line from a file and use it to add acting credits
	 * to a MediaMaker object stored in a LinkedHashMap.
	 * 
	 * 1st: The method will isolate an actor NAME, create a MediaMaker object
	 * which will be stored in the HashMap. The actors name will be the key, The
	 * MediaMaker object will be the value stored.
	 * 
	 * 
	 * **When a MediaMaker object is created it will initialize several empty
	 * ArrayLists of <Series> or <Movies> which can be accessed with the
	 * MediaMaker class' add methods.**
	 * 
	 * 2nd: The method will call parseMovie or parseSeries depending on the
	 * Media Type. Some kind of marker will be used to determine which type
	 * we're dealing with.
	 * 
	 * 3rd: The Movie or Series will be added to the LinkedHashMap. You can
	 * access the object with
	 * The parse
	 * movie method will add any movies to the list of Media objects that arent
	 * already in there so dont worry about doing that here.
	 * 
	 * ***NOTE*** We will have to nest this process in some kind of loop that
	 * repeats it until it comes across a line that has a new actor's name.
	 * 
	 * 
	 * @param fileLine
	 *            A line from the Actors file.
	 * @param people
	 *            A HashMap where the info will be stored.
	 */
	public MediaMaker parseActingCredits(String line, LinkedHashMap<String, MediaMaker> people, List<Media> list) {
		//mediaMaker credentials to create keys
		String lastName = "";
		String firstName = "";
		String additionalPersonInfo = "";
		String fullName = "";
		
		
		//This will allow the creation of a MediaMaker object
		// that will be placed into the LinkedHashMap
		String billingOrder = "";
		String role = "";
		String credit = "";
		boolean suspended = false;
		String mediaType = "";
		String additonalMovieRelease = "";
		Integer movieRelease = new Integer(0);
		String year = "";
		String title = "";
		int index = 0;
		String seasonNumber = "";
		String episodeNumber = "";
		String episodeTitle = "";
		String episodeAppearance = "";
		String episodeReleaseYear = "";
		Integer episodeRelease = new Integer(0);
		
		
		//this will keep track of where the first Name ends
		int count = 0;
		//lets check if we are adding someone or if we are looking for someone in the list.
		if (!whiteSpace(line.charAt(count))) {
			//generates Actors last Name
			while(!removeComma(line.charAt(count)));
			lastName = lastName + line.charAt(count);
			++count;
			while (!whiteSpace(line.charAt(count))) 
			{
				//removes comma that seperates name
				if(removeComma(line.charAt(count))) {
					++count;
				}
				firstName = firstName + line.charAt(count);
				++count;

			}
			while(whiteSpace(line.charAt(count)))
			{
				++count;
				if (isParentheses(line.charAt(count)))
				{
					while(letterCheck(line.charAt(count))) 
					{
						additionalPersonInfo = additionalPersonInfo + line.charAt(count);
						++count;
					}
				}
			}
		if (firstName.length() > 0 && lastName.length() > 0) {
			fullName = firstName + " " + lastName;
		}
		else if (firstName.length() > 0 && lastName.length() > 0 && additionalPersonInfo.length() > 0) {
			fullName = firstName + " " + lastName + " (" + additionalPersonInfo + ")";
		}
		
		index = line.length() - 1;
		
		while (whiteSpace(line.charAt(index))) {
			//remove whiteSpace
			--index;
			if(removeArrow(line.charAt(index))){
			--index;
				while(!removeArrow(line.charAt(index))) {
					billingOrder = line.charAt(index) + billingOrder;
					--index;
				}
			}
			//remove last arrow
			--index; 
			
			if(removeBracket(line.charAt(index))) {
				--index;
				while(!removeBracket(line.charAt(index))) {
					role = line.charAt(index) + role;
					--index;
				}
			}
			//remove last bracket
			--index; 
			if(isParentheses(line.charAt(index))) {
				while (!isParentheses(line.charAt(index))) {
					credit = line.charAt(index) + credit;
					--index;
				}
			}
			//remove parentheses
			--index;
			if(removeCurlyBrace(line.charAt(index))) {
				--index;
				if (removeCurlyBrace(line.charAt(index))) {
					--index;
					while(!removeCurlyBrace(line.charAt(index))) 
					{
						//checking to see if its suspended
						suspended = true;
						--index;
						while (removeCurlyBrace(line.charAt(index))) 
						{
							--index;
						}
					
					}
				}
					
			}
			
			if (removeCurlyBrace(line.charAt(index))) {
				--index;
				if(isParentheses(line.charAt(index))) {
					--index;
					if(isDigit(line.charAt(index))) {
						while(isDigit(line.charAt(index))) {
							episodeNumber = line.charAt(index) + episodeNumber;
							--index;
							if(removeDashSlashAndPeriods(line.charAt(index))) {
								--index;
								while(isDigit(line.charAt(index))) {
									seasonNumber = line.charAt(index) + seasonNumber;
									--index;
									if (removeHastag(line.charAt(index))) {
										//not needed
										--index;
										if(isParentheses(line.charAt(index))) {
											--index;
										}
									}
									else if (removeDashSlashAndPeriods(line.charAt(index))) {
										--index;
										while(isDigit(line.charAt(index))) {
											episodeReleaseYear = line.charAt(index) + episodeReleaseYear;
											--index;
											if(isParentheses(line.charAt(index))) {
												--index;
												if (removeCurlyBrace(line.charAt(index))) {
													--index;
												}
											}
										}
									}
								}
							}
						}
						
					}
				}
				while (!removeCurlyBrace(line.charAt(index))) {
					episodeTitle = line.charAt(index) + episodeTitle;
					--index;
					if(removeCurlyBrace(line.charAt(index))) {
						--index;
						if (removeCurlyBrace(line.charAt(index))) {
							--index;
						}
					}
				}
				
			}
			
	
			if(isParentheses(line.charAt(index))) {
				--index;
				while(isReleaseType(line.charAt(index))) {
					mediaType = line.charAt(index) + mediaType;
					--index;
					if(isParentheses(line.charAt(index))) {
						--index;
					}
				}
			}
			
			if(isParentheses(line.charAt(index))) {
				--index;
				if(letterCheck(line.charAt(index))) {
					additonalMovieRelease = line.charAt(index) + additonalMovieRelease;
					--index;
					if(removeDashSlashAndPeriods(line.charAt(index))) {
						--index;
					}
				}
				while(!isParentheses(line.charAt(index))) {
					year = line.charAt(index) + year;
					--index;
				}
			}
			--index;
			
			
			while (index <= count) {
				title = line.charAt(index) + title;
				--index;
			}
			
			if (episodeTitle.length() > 0 && episodeReleaseYear.length() > 0) 
			{
				
					episodeRelease = Integer.parseInt(episodeReleaseYear);
					Series series = new Series(episodeTitle, episodeRelease, null);
					MediaMaker person = new MediaMaker();
					list.add(series);
					person.addSeriesActed(series);
					person.addObject(fullName, person);
			}
			else if (episodeTitle.length() > 0 && seasonNumber.length() > 0 && episodeNumber.length() > 0) {
				
				episodeRelease = Integer.parseInt(episodeReleaseYear);
				EpisodeTV episode = new EpisodeTV(episodeTitle, episodeRelease, title, Integer.parseInt(seasonNumber), Integer.parseInt(episodeNumber), suspended);
				MediaMaker person = new MediaMaker();
				Series series = new Series(episodeTitle, episodeRelease, null);
				Media media = series;
				list.add(episode);
				person.addSeriesActed(series);
				person.addObject(fullName, person);
				}
			
			else if (episodeTitle.length() == 0 && seasonNumber.length() == 0 && episodeNumber.length() == 0) {
					movieRelease = Integer.parseInt(year);
					Movies movies = new Movies(title, movieRelease, mediaType);
					Media media = movies;
					list.add(media);
					MediaMaker person = new MediaMaker();
					person.addMovieActed(movies);
					
			}
			
		}
		
			
		}
		
		//name is already in list
		else if(whiteSpace(line.charAt(index))) {
			
			
			index = line.length();
			
			
			
		}
		
		
		
		
		return null;
		
	}

	/**
	 * This Method will have the same process as "parseActingCredits" but, the
	 * code will be slightly different.
	 * 
	 * @param fileLine
	 * @param people
	 */
	public ArrayList<Media> parseDirectingCredits(String fileLine) {
		
		ArrayList<Media> media = new ArrayList<Media>();
		return media;
	}

	/**
	 * This Method will have the same process as "parseActingCredits" but, the
	 * code will be slightly different.
	 * 
	 * @param fileLine
	 * @param people
	 */
	public ArrayList<Media> parseProducingCredits(String fileLine) {
		
		
		ArrayList<Media> media = new ArrayList<Media>();
		return media;
	}

	/**
	 * This method will contain the code necessary to get user input form the
	 * keyboard to determine course of action for the program.
	 */

}
