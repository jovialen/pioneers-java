---
tags:
  - graph
  - scene
---
A graph-based approach to structuring a scene system. Every scene is defined by collections of [[Scene Node]]s that each have their own behavior. Additionally, every node have children which are also scene nodes. 

Every scene has a root node that owns every other node in the scene. Each tick, the root node is updated, then all of its children in a depth-first approach. 