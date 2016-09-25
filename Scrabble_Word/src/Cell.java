import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


class Cell extends Pane{
		
		/**
		 * 
		 */
		private final CellBox cellBox;
		public char cellLetter;
	//	private Label letterLabel;
		private Text cellLetterText;
		private int cellNumber;
		private int cellRow;
		private int cellCollum;
		
		private Boolean mainBoardLetter=false;
		private Boolean clientLetter=false;
		
		private char cellPreviousLetter;
		
		
		
		private void handleMouseClick()
		{
			if (this.cellBox.myTurn)
			{
		//		System.out.println(cellLetter +" has been clicked row is "+cellRow+" col is "+cellCollum+" and cellnumber "+cellNumber);
				if (this.cellBox.canEditWord==false && mainBoardLetter)
				{
				//	System.out.println(cellLetter +" has been chosen");
					if (this.cellBox.isFirstLetterChosen==false)
					{
						this.cellBox.coOrdinate.startRow=cellRow;
						this.cellBox.coOrdinate.startCol=cellCollum;
						
						this.cellBox.isFirstLetterChosen=true;
						
					}
					else
					{		
						this.cellBox.coOrdinate.endRow=cellRow;
						this.cellBox.coOrdinate.endCol=cellCollum;
						this.cellBox.coOrdinate.findNatureOfWord();
						
						
						this.cellBox.statusAboutWord.setText("first and last letter has been chosen");					
						this.cellBox.isFirstLetterChosen=false;
						this.cellBox.canEditWord=true;
						this.cellBox.newlyCreatedWord();
						
					}
					
				
				}
				else if (this.cellBox.canEditWord)
				{
				
				//	System.out.println("cellnumber "+cellNumber+" userselectedprenum "+userSelectedPreviousLetterNumber+" "+canWriteToMainBoard);
					
					if (cellNumber==CellBox.userSelectedPreviousLetterNumber )
					{
						changeLetter(cellPreviousLetter);
						this.cellBox.status.setText("discarding your previous selection");
						
					}
					
					else if (CellBox.canWriteToMainBoard && mainBoardLetter)
					{
						if (this.cellLetter!=' ')
						{
							this.cellBox.status.setText("you cant write here here already letter "+ cellLetter +" exists \n");
						}
						else{
						
							changeLetter(CellBox.userSelectedLetter);
							
							
						//	userSelectedLetter='z';
							System.out.println("going to make canwritetomotherboard false");
						//	canWriteToMainBoard=false;
						//	userSelectedPreviousLetterNumber=cellNumber;
							this.cellBox.status.setText("successfully changed the motherBoardLetter");
						//	repaint();
						}
					}
					
					else if (CellBox.canWriteToMainBoard==false && clientLetter)
					{
						System.out.println("cellnumber "+cellNumber+" userselectedprenum "+CellBox.userSelectedPreviousLetterNumber);
		
						if (cellLetter=='X')
						{
							this.cellBox.status.setText("you cant choose previously Selected client... latter");
						}
						
						else{
							this.cellBox.status.setText("now user selected letter is "+ cellLetter);
							System.out.println(this.cellLetter);
							changeLetter('X');
						
							CellBox.canWriteToMainBoard=true;
					
						}
						
						
					}
					
					else{
						this.cellBox.status.setText("you are not supposed to press this letter");			
					}
					
				}
			
			}
		}
		
		
		
	
		
		public Cell(CellBox cellBox, char ch,char whichBoard,int i,int j) {
			this.cellBox = cellBox;
			if (whichBoard=='m')
			{
				mainBoardLetter=true;
			}
			if (whichBoard=='c')
			{
				clientLetter=true;
			}
			cellRow=i;
			cellCollum=j;
			cellNumber=CellBox.staticCellNumber++;
			cellLetter=ch;
			this.setPrefSize(40, 40);
			this.setStyle("-fx-border-color:black;");
			this.setOnMouseClicked(e-> handleMouseClick());
//			letterLabel=new Label(" "+letter+" ");
//			this.getChildren().add(letterLabel);
			cellLetterText=new Text(10,10,cellLetter+"");
			if (mainBoardLetter)
			{
				cellLetterText.setId("board");
			}
			else{
				cellLetterText.setId("client");				
				
			}
			cellLetterText.xProperty().bind(this.widthProperty().divide(2));
			cellLetterText.yProperty().bind(this.heightProperty().divide(2));
			
			this.getChildren().add(cellLetterText);
			
			this.setStyle("-fx-border-color: blue;-fx-border-insets: 2;");
	
		}
		
		public char getLetter() {
			return cellLetter;
		}
		
		public void change()
		{
			repaint();
		}
		
		public void changeLetter(char ch)
		{
			cellPreviousLetter=cellLetter;
			CellBox.userSelectedLetter=cellLetter;
			CellBox.userSelectedPreviousLetterNumber=cellNumber;
			
			cellLetter=ch;
			if (CellBox.canWriteToMainBoard)
			{
				CellBox.canWriteToMainBoard=false;
			}
			else{
				CellBox.canWriteToMainBoard=true;
			}
			repaint();
			
		}
		
//		protected void repaint(char ch)
//		{
//		//	cellLetterText.setText(ch+"");			
//			
//		}
		protected void repaint()
		{
		//	System.out.println(" is trying to repaint ");
			
			cellLetterText.setText(cellLetter+"");
			
		
		}
		
		
		
	}