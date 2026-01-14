import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

type TeamMember = {
  name: string;
  role: string;
  imgSrc: string;
  imgAlt: string;
};

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './about-us.html',
  styleUrl: './about-us.scss',
})
export class AboutUsComponent {
  members: TeamMember[] = [
    {
      name: 'Dina Adel',
      role: 'Responsible for style and design as well as setting up the github project structure.',
      imgSrc: 'assets/team/team-1.jpg',
      imgAlt: 'Portrait of Member 1',
    },
    {
      name: 'Ozan Aksu',
      role: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      imgSrc: 'assets/team/team-2.jpg',
      imgAlt: 'Portrait of Member 2',
    },
    {
      name: 'Sakshi Dilip Mehta',
      role: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      imgSrc: 'assets/team/team-3.jpg',
      imgAlt: 'Portrait of Member 3',
    },
    {
      name: 'Manisha Shekhar Mirje',
      role: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      imgSrc: 'assets/team/team-4.jpg',
      imgAlt: 'Portrait of Member 4',
    },
    {
      name: 'Muhammad Shahbaz',
      role: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      imgSrc: 'assets/team/team-5.jpg',
      imgAlt: 'Portrait of Member 5',
    },
  ];
}