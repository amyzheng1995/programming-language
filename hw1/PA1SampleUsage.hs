import PA1Helper

-- Haskell representation of lambda expression
-- In Lambda Lexp Lexp, the first Lexp should always be Atom String
-- data Lexp = Atom String | Lambda Lexp Lexp | Apply Lexp  Lexp 

-- Given a filename and function for reducing lambda expressions,
-- reduce all valid lambda expressions in the file and output results.
-- runProgram :: String -> (Lexp -> Lexp) -> IO()

-- This is the identity function for the Lexp datatype, which is
-- used to illustrate pattern matching with the datatype. "_" was
-- used since I did not need to use bound variable. For your code,
-- however, you can replace "_" with an actual variable name so you
-- can use the bound variable. The "@" allows you to retain a variable
-- that represents the entire structure, while pattern matching on
-- components of the structure.
--id' :: Lexp -> Lexp
--id' v@(Atom _) = v
--id' lexp@(Lambda (Atom _) _) = lexp
--id' lexp@(Apply _ _) = lexp

-- All possible letters
letters = [Atom "a", Atom "b", Atom "c", Atom "d", Atom "e", Atom "f", Atom "g", Atom "h", Atom "i", Atom "j", Atom "k", Atom "l", Atom "m", Atom "n", Atom "o", Atom "p", Atom "q", Atom "r", Atom "s", Atom "t", Atom "u", Atom "v", Atom "w", Atom "x", Atom "y", Atom "z"]

-- Get all the atoms in an expression, takes in a expression and returns a list of expression
-- first case v- append v to the list
-- second case Lambda a e- take in a, and recurse over the expression to append all the atoms
-- third case (e e)- recurse over both expressions and combine the list
allatoms :: Lexp -> [Lexp]
allatoms v@(Atom _) = [v] 
allatoms lexp@(Lambda a e1) = [a] ++ (allatoms e1)
allatoms lexp@(Apply e1 e2) = (allatoms e1) ++ (allatoms e2)

-- get the first fresh atom in letters that is not in all atoms
-- if the head of letters appear in the allatoms list, keep recurse.
-- else return that atom in letters
freshatom :: [Lexp] -> [Lexp] -> Lexp
freshatom l1 (x:xs) = if elem x l1 then freshatom l1 xs 
                      else x

-- Takes in an expression, free atom, and a substitute atom returns an expression (helper functionf or a_replace, takes care of all case of e)
-- first case v- if this atom is the one we are looking for, replace it with the substitute atom. else just keep the current atom
-- second case Lambda a e- if atom a is the free atom we are looking for, then don't do anything. if it's not, recurse on e1
-- third case (e e) - recurse over both expressions, then return the expression in its own format
replace_free :: Lexp -> Lexp -> Lexp -> Lexp
replace_free v@(Atom a) (Atom free) (Atom sub) = if a == free then (Atom sub) else (Atom a)
replace_free lexp@(Lambda a e1) (Atom free) (Atom sub) = if a == (Atom free) then lexp
                                                         else (Lambda a (replace_free e1 (Atom free) (Atom sub)))
replace_free lexp@(Apply e1 e2) (Atom free) (Atom sub) = let temp1 = replace_free e1 (Atom free) (Atom sub)
                                                             temp2 = replace_free e2 (Atom free) (Atom sub)
                                                         in (Apply temp1 temp2)


-- it replaces the bounded variables in the form Lambda A E.
-- checks if atom a is bounded, if it is then find all the atom a's in the e1 and substitute it with b
a_replace :: Lexp -> Lexp -> Lexp
a_replace lexp@(Lambda (Atom a) e1 ) (Atom b) = if elem (Atom a) (isfree e1) then (Lambda (Atom b) (replace_free e1 (Atom a) (Atom b)))
                                                else lexp

--alpha renaming
alpha :: Lexp -> Lexp 
alpha v@(Atom a) = v
alpha lexp@(Lambda(Atom a) e1) = let e2 = alpha e1
                                     fresh = freshatom (allatoms (Lambda (Atom a) e2)) letters -- creating a fresh atom
                                 in a_replace (Lambda (Atom a) e2) fresh -- replace with the new atom

alpha lexp@(Apply e1 e2) = let temp1 = alpha e1
                               temp2 = alpha e2
                           in (Apply temp1 temp2)

-- Takes in 3 arguments, first is the expression that we want to find the bonded variable.
--   second is the atom
--   third is the the expression that we want to substitute
replace :: Lexp -> Lexp -> Lexp -> Lexp 
replace v@(Atom a) x sub = if v == x then sub else v 
replace lexp@(Lambda a e1) x sub = if a == x then lexp
                                   else let e1_x = replace e1 x sub in Lambda a e1_x
replace lexp@(Apply e1 e2) x sub = let e1_x = replace e1 x sub
                                       e2_x = replace e2 x sub 
                                   in  Apply e1_x e2_x

-- beta reduction
-- concentrate on the case of lambda.x E M, in this case, we use the replace helper function to replace all the x in the E with M
beta :: Lexp -> Lexp
beta v@(Atom _) = v
beta lexp@(Lambda(Atom a) e1) = let temp1 = beta e1
                                in (Lambda (Atom a) temp1)

beta lexp@(Apply e1 e2) = let temp1 = beta e1 
                              temp2 = beta e2
                          in case temp1 of 
                                  (Lambda x e) -> beta (replace e x temp2)
                                  --_ -> (Apply temp1 temp2)
                                  _ -> (Apply (beta temp1) (beta temp2))
                             


-- return a list of free variables in an expression
remove :: Lexp -> [Lexp] -> [Lexp]
remove a l = filter (\v -> v /= a) l

isfree :: Lexp -> [Lexp]	
isfree v@(Atom _) = [v]
isfree lexp@(Lambda a e1) = remove a (isfree e1)
isfree lexp@(Apply e1 e2) = (isfree e1) ++ (isfree e2)


--eta conversion
-- concentrate on the case lambda v. x v, if v is equal to v and v is bound, return x
-- to determine if v is bound, you check to see if there is any v in expression x
eta :: Lexp -> Lexp
eta v@(Atom _) = v
eta lexp@(Lambda (Atom a) e1) = let temp1 = eta e1
                                in case temp1 of 
                                        (Apply e2 (Atom a)) -> if notElem (Atom a) (isfree e2) then e2
                                                               else (Lambda (Atom a) temp1)
                                        _ -> (Lambda (Atom a) temp1)
                                
eta lexp@(Apply e1 e2) = (Apply (eta e1) (eta e2))


-- Entry point of program
main = do
    putStrLn "Please enter a filename containing lambda expressions:"
    fileName <- getLine
    -- id' simply returns its input, so runProgram will result
    -- in printing each lambda expression twice. 
    runProgram fileName ( eta . beta . alpha)