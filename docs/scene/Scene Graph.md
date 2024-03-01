---
tags:
  - graph
  - scene
---
A graph-based approach to structuring a scene system. Every scene is defined by collections of [[Scene Node]]s that each have their own behavior. Additionally, every node have children which are also scene nodes. 

The scene graph has no inherit behavior of its own, but serves only to propagate events through the tree. Any actual behavior is implemented in the scene nodes within the tree. 

Any event received by the tree is distributed in the following manner: The event is given to the root node, which is given the opportunity to process the event, before the event is then sent to all the nodes children in a depth-first approach. 
