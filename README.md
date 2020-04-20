# CSC212Associations
Tree and Hash assignment for CSC212 S2020 (remote).

## About this Assignment

This assignment has fewer missing methods than our previous assignments but there's comparatively more content, and more thinking involved in each. All the tree methods require thinking about the recursive structure.

- ***IntBST*** - a binary search tree storing a set of integers.
    - TODO: implement ``BSTNode.addToSortedList`` (in-order traversal)
    - TODO: implement ``BSTNode.getHeight`` - recursive height-calculation.
    - TODO: implement ``BSTNode.contains`` - recursive search for a specific value (insert/remove done for you).
- ***IntExpr*** - a "mathematical" expressions tree that evaluates over integers.
    - TODO: implement ``BinaryExpr.evaluate`` for op=``+``,``-``,``/``,``*``, and ``%``. ``IfExpr`` and ``UnaryExpr`` are examples.
- ***StrIntHashMap*** - a hashmap with singly-linked-lists as buckets. 
    - TODO: implement ``StrIntHashMap.get`` (easier), and ``StrIntHashMap.put`` (medium). ``StrIntHashMap.remove`` (hard) is completed for you as a rosetta stone for how the data structure works.

## Grading (Automatic)

Right now, when I run all the tests in this project, I get the following output:

```
Tests run: 33, Failures: 0, Errors: 21, Skipped: 0
```

So there are 12 passing tests, and 21 failing tests.

Your score on this assignment will range up to 100% -- the fraction of the tests that pass on your code (minus the tests that already pass), provided:

 1. you do not use uncited online resources.
 2. there are no code compilation problems with your code (red lines or Problems in Eclipse).  
 3. you do not rename any methods or modify the tests in any way, and 
 4. you resolve any infinite loops in your code (better to comment out that method and have it crash than run forever -- it prevents other tests from running.

