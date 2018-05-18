//2017243003���ּ�
//���ڸ��ų� ��κ��� �𸣰ڰų� ���ذ� ���� �ȵȺκ��Դϴ�
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
	new_node->data = k;//data�� ���� newnode�� ����
	new_node->next = tail;
	node* temp = head;
	while (1)
	{
		node* next_node = temp->next;
		if (next_node == tail)
		{
			temp->next = new_node;//new node�� next������ �̵�
			new_node->next = tail; break;//tail�� new node�� next ������ �̵�
		}
		if (next_node->data > k)
		{
			new_node->next = temp->next;//temp�� �ؽ�Ʈ ���� new�����  next������ �̵�
			temp->next = new_node;//newnode�� next������ �̵�
		}
		temp = temp->next;//next���� temp�����ε�
		

	}
}

node *print_list(node*t){
	printf("Start=====================\n");
	node p;
	//������ �κ��� ���ϰڽ��ϴ�
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
	print_list(head->next);//head�� next�� ���
	
	delete_node(8);//node8����
	print_list(head->next);
	

}