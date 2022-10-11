# Project_4
Code for Project 4 in CSCI4448-OOAD

Team: Devin Riess, Henry Saver

Version: Java 8 - Oracle

Notes/Assumptions:

TREASURE can be found in src.main.world.objects \
FIGHT Attributes can be found in src.main.fight \
DECORATOR Celebration class can be found in src.main.fight.celebration \
LOGGER logs can be found at main.world.logs

**JUNIT NOTES**
Go into the WorldTest class inside of the Test folder to find our Junit tests as well as a captured output of the Junit tests.



The only assumption we made that was not clear in the docs is the case for players that start in a room with monsters. Our implementation allows them to flee from the room instead of fighting ONLY if they started in that room. If they end up in a room with monsters after fighting, they still are forced to engage, per our interpretation of the docs. This is our intended behavior.
Project 3 additional note: We made the decision for portals to instantly be used upon pickup because portals are
super duper cool so what kind of adventurer wouldn't immediately use one?