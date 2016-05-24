% Assign number 1-9
num(1).
num(2).
num(3).
num(4).
num(5).
num(6).
num(7).
num(8).
num(9).

% sum any three numbers to 17
sum(A, B, C):-
		num(A),
		num(B),
		num(C),
		(A+B+C)=:=17.

% list all the possibilities and sort as well.
puzzle([A, B, C, D, E, F, G, H, I]):-
		sum(A, B, D),
		sum(A, C, F),
		sum(D, G, I),
		sum(D, E, F),
		sum(F, H, I),
		sort([A,B,C,D,E,F,G,H,I], [1,2,3,4,5,6,7,8,9]).