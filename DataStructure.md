# Data structure of the World #

## General information ##
The World of destal-open-world is divided twice: First of all, it has several levels the player can move on. However, every level is divided into smaller areas, the so-called "Chunks". They provide a faster loading of the map since they enable to control how much of the world - or better: of the current level - should be loaded.
Right now, we are planning to simultaneously load nine chunks - the current one plus the eight ones around it.

## Data structure of the World ##
A world file uses the extension _.world_. Right now, it is generated but not used.
All worlds are saved in the folder _world_ in the program directory.
This folder contains the World files of the worlds plus the level folders, named lvl\_WorldName.

## Data structure of the Levels ##
In the level folder of each World, there are several Level files called _lvl0.lvl_, _lvl1.lvl_ etc. plus one folder per level used to save the Chunks. These folders are called chunk\_0, chunk\_1 etc.

## Data structure of the Chunks ##
Inside the Chunk folders, the different chunks are stored in files which are consecutively numbered using two indexes for the chunk's horizontal and vertical position.
The size of the files is specified in the constant general.World.CHUNK\_SIZE. Probably one chunk will have the size 64x64, so that every chunk file will have the size of exactly 4 kibibytes.