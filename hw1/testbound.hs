import PA1Helper

--allatoms :: Lexp -> [Lexp]
--allatoms v@(Atom _) = [v]
--allatoms lexp@(Lambda a e1) = [a] ++ (allatoms e1)
--allatoms lexp@(Apply e1 e2) = (allatoms e1) ++ (allatoms e2)

remove :: Lexp -> [Lexp] -> [Lexp]
remove a l = filter (\v -> v /= a) l

isfree :: Lexp -> [Lexp]	
isfree v@(Atom _) = [v]
isfree lexp@(Lambda a e1) = remove a (isfree e1)
isfree lexp@(Apply e1 e2) = (isfree e1) ++ (isfree e2)