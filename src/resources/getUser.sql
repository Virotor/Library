



select * from Books where BookName  like 'Аспид'

SELECT B.BookId, BookName, BookAuthors, isReturned, YearOfPub  From BookIssuance
                join Books B on BookIssuance.BookId = B.BookId
                where UserId = 1