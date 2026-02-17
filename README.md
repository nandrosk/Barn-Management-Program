# My Personal Project

## What will this project be?

This project will be a program that is designed for stables, where a user can keep track of the information of all the horses in the stable. It will also allow a user to create a schedule of which pastures horses will be put in each day of the week (a turnout board). 

Some functionality of this project will include:
- Adding and viewing information about the horses in the barn
- Viewing which pastures horses will be in on a given day
- Adding and viewing notes left on specific days
- Creating a weekly turnout schedule

## Who is this project for?

I'm making this project specifically for my mom, to use to help streamline some processes involved in running our family's horse boarding business. It's intended to be used by barn owners, staff, and potentially boarders as well. 

## Why did I decide to make this project?

When I was choosing what I wanted to create, I knew from the beginning that I wanted to make a project that would be useful and fulfill an actual, specific purpose. My family owns a stable and has a business boarding horses, and when I spoke to my mom she said that a program to help keep track of the horses would be helpful. Specifically, a program to streamline the process of making the daily turnout board. So that's what I decided to make! My ultimate goal is for my program to be helpful in the day-to-day of running the barn. I've worked at our barn for years and it means a lot to me, so the personal experience and connection also motivates me in bringing this program to life.


## User Stories

- I want to be able to add and remove horses from the barn
- I want to be able to add/update information about each horse, including things like name, owner, vet, farrier, feeding information, etc.
- I want to be able to view the list of horses in the barn
- I want to be able to view information about a specific horse
- I want to be able to update the pasture a horse will be in on a given day
- I want to be able to view which horses are in which pastures on a given day
- I want to be able to add and view notes on a given day (to specify things like if a horse has a vet appointment or lesson that day)

- I want to be given the option to load my saved data 
- I want to be given the option to save my data
- I want to be prompted to save my data when attempting to quit the application

## Instructions for End User

- You can view the panel that displays the Xs (horses) that have already been added to the Y (barn) by simply running the main method in the Main class
- You can generate the first required action related to the user story "adding multiple horsess to a barn" by entering information into the three associated fields and pressing the "Add Horse" button
- You can generate the second required action related to the user story "changing a horse's information" by selecting a horse and pressing the "View Info" button. Then, in the new window that opens, selecting the piece of information you want to change, entering the new information into the field, and pressing the "Change Info" button.
- You can locate my visual component by simply running the program. I have added a cute banner image at the top.
- You can save the state of my application by pressing the save button on the main window.
- You can reload the state of my application by pressing the load button on the main window.

## Phase 4: Task 2
- Horse added: Fig
- Horse removed: Brio
- Horse name changed from Livvy to Stella

## Phase 4: Task 3

One of the big things I would consider refactoring is the way the Day class stores information about which horses are in which pastures. At the moment, it uses a list of lists of horses. It works, but it is a bit clunky, so I would consider creating a Pasture class to encapsulate the information in a neater way. The issue is just that needing to store which horses are in which pastures on which day will always have some layers due to the nature of the information. Another good option I would consider is switching it over from a list to a set and map. The order of horses in a pasture doesn't matter, so horses could be put in a set that is then mapped to the name of the pasture. 

Another big thing I would consider doing is separating out the basic barn management functionality and the specific turnout board functionality. This would make it so that you could have a barn with horses and such and be able to use that code in other, more general applications without necessarily having to take the turnout board functionality along with you. It would also then simplify the cascading list of information, with a Barn having a Schedule, which has Days, which has Notes, etc. I could potentially change the structure so that there is a TurnoutBoard that has a list of Notes, Pastures, and Horses. Then, instead of a note being contained within a day, it would contain a field for which day it's for. Changing over to this model would require some more complicated methods for organizing the notes by day and such, but would simplify the class relationships. There would be a lot of tradeoffs, so it depends on what type of complexity you'd prefer to deal with. 
