---
tags:
  - graph
  - scene
---
A local scene graph is a [[Scene Graph]] with a node or other scene graph as its parent. A [[Scene Node]] can then reference the local scene graph in addition to the [[Main Scene Graph]] separately. 

The purpose of local scene graphs is to serve as a type of [[Prefab]] that can be instantiated several times inside a scene. Functionally they are identical to any other scene graph, the only difference being in how they are instantiated. 