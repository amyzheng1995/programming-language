% This allows you to load the program read_line.pl
% This will allow you to use the read_line predicate in your source file.
:- [read_line].
:- dynamic neg/2.
:- dynamic fact/2.
% This is the top-level term for this toy grammar.
% Note that the the period is also captured when using read_line so
% we account for this by capturing the period in the grammar using the
% end term.
% This rule also introduces the capture of input through our X variable.


parse_and_assert():-phrase(fact(Fact), Input),assert(Fact).
%the <noun> _ did not <pres_verb>.
fact(pos(Noun,Verb)) --> [the, Noun, _ , Verb,'.'].
fact(neg(Noun,Pre_Verb)) --> [the, Noun, _, did, not, Pre_Verb,'.'].

%did a <noun> <pres_verb>?
%did every <noun> <pres_verb>?

parse_and_query() :- phrase(q(Result),Input), judge(Result).
q(single(Noun,Pre_Verb)) --> [did, a, Noun, Pre_Verb, '?'].
q(all(Noun,Pre_Verb)) --> [did, every, Noun, Pre_Verb, '?'].



judge(single(Noun, Pre_Verb)) :- pastTenseOf(Pre_Verb,Verb),pos(Noun, Verb), !,write(yes).
judge(single(Noun, Pre_Verb)) :- write(no).
judge(all(Noun,Pre_Verb)) :- pastTenseOf(Pre_Verb,Verb), neg(Noun, Pre_Verb), !, write(no).
judge(all(Noun,Pre_Verb)) :- write(yes).

pastTenseOf(leave,left).
pastTenseOf(fly, flew).
pastTenseOf(arrive,arrived).
pastTenseOf(stay, stayed).


% Below are two ways of parsing input using your grammar.
% The first way exposes what Prolog does behind the scenes when you
% use the Definite Clause Grammar (DCG) syntax.
% Full understanding of how DCG syntax is converted into Prolog requires
% understanding of accumulators and difference lists, but full understanding
% is not required to solve this homework.
% parse(Tree,Sentence) :- word(Tree,Sentence,[]).
% This second rule relies on a built in predicate 'phrase' to work with
% the DCG syntax. The phrase predicate hides the details of how DCG is
% converted into Prolog.
parse(Input) :- phrase(fact(Fact), Input), assert(Fact).
parse(Input) :- phrase(q(Result), Input), judge(Result).

% This predicate reads the user input, parses it to see if it is accepted
% by the grammar, and outputs the resulting parse tree if the grammar is
% accepted.
loop :- read_line(Sent), parse(Sent), nl, loop.