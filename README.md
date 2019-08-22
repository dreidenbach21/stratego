# stratego
To play run Game.java and upon start up you will see what is the first image below, an empy user field. To fill your field click the block you want and press 2 3 4 5 6 7 8 9 1(10) s(spy) b(bomb) f(flag) to place your piece. The game will start and the user goes first ones you place your last piece. The game will end eith when all of one sides movable pieces are captured or when a flag is captured. The AI works with a iterative deepening DFS through a game tree with nodes containing game states that hold all information including board layout. The run time is increased with the alpha beta pruning algorithm to reduce the exponetial time to move through the tree.

![start](https://i.postimg.cc/fydjpsk9/Screen-Shot-2019-08-21-at-10-25-02-PM.png)

![mid user selection](https://i.postimg.cc/c4QBcdpq/Screen-Shot-2019-08-21-at-10-25-23-PM.png)

![game example 1](https://i.postimg.cc/K8xrqdb1/Screen-Shot-2019-08-21-at-10-26-32-PM.png)

![game example 2](https://i.postimg.cc/hts8Hp4p/Screen-Shot-2019-08-21-at-10-28-45-PM.png)
