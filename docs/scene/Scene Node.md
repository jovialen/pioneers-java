---
tags:
  - scene
  - graph
  - node
---
A scene node is a node within a [[Scene Graph]]. Every node has two main components, its behavior and its relationships.

A node has exactly one parent, and any amount of children, both of which are other scene nodes. It can reference both freely at any time. Additionally, the node can also reference the roots of the  [[Local Scene Graph]] root and the [[Main Scene Graph]]. 
