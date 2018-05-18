//2017243003박주성
//모자르거나 빈부분은 모르겠거나 이해가 아직 안된부분입니다
#include<stdio.h>
#include<stdlib.h>

typedef struct _node {
	int data;
	struct _node *next;
}node;
node *head, *tail;

void init_list(void) {
	head = (node*)malloc(sizeof(node));
	tail = (node*)malloc(sizeof(node));
	head->next = tail;
	tail->next = tail;
}

node *ordered_insert(int k) { 
	node* new_node = (node*)malloc(sizeof(node));
	new_node->data = k;//data의 값은 newnode의 값임
	new_node->next = tail;
	node* temp = head;
	while (1)
	{
		node* next_node = temp->next;
		if (next_node == tail)
		{
			temp->next = new_node;//new node가 next값으로 이동
			new_node->next = tail; break;//tail이 new node의 next 값으로 이동
		}
		if (next_node->data > k)
		{
			new_node->next = temp->next;//temp의 넥스트 값이 new노드의  next값으로 이동
			temp->next = new_node;//newnode가 next값으로 이동
		}
		temp = temp->next;//next값이 temp값으로됨
		

	}
}

node *print_list(node*t){
	printf("Start=====================\n");
	node p;
	//나머지 부분은 못하겠습니다
}
int delete_node(int k){}

void main()
{
	node *t;

	init_list();
	ordered_insert(10);
	ordered_insert(5);
	ordered_insert(8);
	ordered_insert(3);
	ordered_insert(1);
	ordered_insert(7);

	printf("\nInitial Linked list is");
	print_list(head->next);//head의 next값 출력
	
	delete_node(8);//node8제거
	print_list(head->next);
	

}