Group Member:
Amy Zheng (zhengz3)
Robert Anthony Russo (russor3)

Feature:

Our current code passes all the test cases. We implemented beta, eta, and alpha.

Logic:
alpha:
Letters at the top lists all the possible atoms
find all the atoms in the expression
recurse through the list, find the first one that doesn't appear in allatoms
find the bound variables we want to substitute, and replace these variables with fresh atom

beta:
have a replace function that replace the atom we want ot find with a substitute
recurse on beta whenever we see expressions and return the expression in its original format but with the betaed expression
in Lambda.x e m case, substiute the x in e with m

eta:
find all the free variables in an expression
in Lambda a e case, if the atom a is bounded, just return e