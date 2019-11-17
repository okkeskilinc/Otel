#include<iostream>
#include<conio.h>
#include<string.h>


using namespace std;

struct hotel{
	int id;
	int r_type;
	struct hotel *next;
};

typedef struct hotel h_room;

void room_insert(h_room *x,int i,int r){
	while (x->next=!NULL){
		x=x->next;
	}
	x->next=new h_room();
	x->next->id=i;
	x->next->r_type=r;
	x->next->next=NULL;
	}


int main(){
	
	
	
	getch();
	return 0;
}
