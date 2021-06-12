import { Component } from '@angular/core';

@Component({ templateUrl: 'matchmaking.component.html' })
export class MatchmakingComponent {
  public index = 0;

  constructor() { }

    public genres = [
      {id: 0, name: 'comedy',
        img: 'https://m.media-amazon.com/images/G/01/IMDb/genres/Comedy._CB1513316167_SX233_CR0,0,233,131_AL_.jpg'},
      {id: 1, name: 'sci-fi',
        img: 'https://m.media-amazon.com/images/G/01/IMDb/genres/Sci-Fi._CB1513316168_SX233_CR0,0,233,131_AL_.jpg'},
      {id: 2, name: 'horror',
        img: 'https://m.media-amazon.com/images/G/01/IMDb/genres/Horror._CB1513316168_SX233_CR0,0,233,131_AL_.jpg'},
      {id: 3, name: 'romance',
      img: 'https://m.media-amazon.com/images/G/01/IMDb/genres/Romance._CB1513316168_SX233_CR0,0,233,131_AL_.jpg'},
      {id: 4, name: 'action',
      img: 'https://m.media-amazon.com/images/G/01/IMDb/genres/Action._CB1513316166_SX233_CR0,0,233,131_AL_.jpg'},
      {id: 5, name: 'thriller' },
      {id: 6, name: 'drama' },
      {id: 7, name: 'mystery' },
      {id: 8, name: 'crime' },
      {id: 9, name: 'animation' },
      {id: 10, name: 'adventure' },
      {id: 11, name: 'fantasy' },
      {id: 12, name: 'comedy-romance' },
      {id: 13, name: 'action-comedy' },
      {id: 14, name: 'superhero' }
    ];


     public films = [
      {
        name: 'the office',
        genre: 'comedy',
        year: '2005–2013',
        storyline: 'A mockumentary on a group of typical office workers, where the workday consists of ego clashes, inappropriate behavior, and tedium. ',
        creators: 'Greg Daniels, Ricky Gervais, Stephen Merchant',
        stars: ' Steve Carell, Jenna Fischer, John Krasinski',
        img: 'https://m.media-amazon.com/images/M/MV5BMDNkOTE4NDQtMTNmYi00MWE0LWE4ZTktYTc0NzhhNWIzNzJiXkEyXkFqcGdeQXVyMzQ2MDI5NjU@._V1_UX182_CR0,0,182,268_AL_.jpg'
      },
      {
        name: 'Mortal Kombat',
        genre: 'action',
        year: '2021',
        storyline: 'MMA fighter Cole Young seeks out Earth\'s greatest champions in order to stand against the enemies of Outworld in a high stakes battle for the universe. ',
        creators: 'Greg Russo, Dave Callaham',
        stars: 'Lewis Tan, Jessica McNamee, Josh Lawson',
        img: 'https://m.media-amazon.com/images/M/MV5BY2ZlNWIxODMtN2YwZi00ZjNmLWIyN2UtZTFkYmZkNDQyNTAyXkEyXkFqcGdeQXVyODkzNTgxMDg@._V1_UX182_CR0,0,182,268_AL_.jpg',
      }
    ];
     formatLabel(value: number): any {
    if (value >= 1000) {
      return Math.round(value / 1000);
    }

    return value;
  }
}


