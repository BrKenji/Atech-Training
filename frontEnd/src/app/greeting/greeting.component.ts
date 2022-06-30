import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-greeting',
  templateUrl: './greeting.component.html',
  styleUrls: ['./greeting.component.css']
})
export class GreetingComponent implements OnInit {

  name: string = ''
  received: boolean = false
  greeting: string = ''
  receivedName: string = ''

  postUrl = 'http://localhost:8080/hello/'


  constructor(private httpClient: HttpClient) { }

  getName(){
    this.received  = false
    if(this.name){
      this.httpClient.post<{greeting:string, name:string}>(this.postUrl, this.name).subscribe(
        result=> {
          this.receivedName = result.name
          this.greeting = result.greeting
          this.received = true
        }
      )
    } else alert("Nome obrigatório !")
  }

  ngOnInit(): void {
  }

}
