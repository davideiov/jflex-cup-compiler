#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <string.h>

//procedure di supporto
char * itos(int n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%d", n);
	return dest;
}

char * dtos(double n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%lf", n);
	return dest;
}

char * btos(bool b) {
	char * dest = malloc(sizeof(char)*2);
	if(b == true)
		dest = "true";
	else
		dest = "false";
	return dest;
}

char * strconcat(char * str1, char * str2) {
	char * dest = malloc(sizeof(char)*256);
	strcat(dest, str1);
	strcat(dest, str2);
	return dest;
}

//dichiarazioni variabili globali

//dichiarazioni funzioni
double add(double a, double b){

	return a + b;
}

int mult(int a, int b){
	int i = 0;
	int result = 0;

	while(i < b){

		result = result + a;
		i = i + 1;
	}
;
	return result;
}

int division(double a, double b){

	return a / b;
}

double myPow(double a, double b){

	return pow(a, b);
}

int fibonacci(int a){

	if(a <= 1){

		return a;
	}
;
	return 	fibonacci(a - 1) + 	fibonacci(a - 2);
}


//main
int main(){
	char * ans = malloc (sizeof(char) * 128);
	double real_a, real_b, real_result =  -1.0;
	int int_a, int_b, int_result =  -1;

	printf("%s", "Benvenuto, inserire il numero corrispondente per effettuare la scelta!");
	printf("\n");
	printf("%s", "1. la somma di due numeri");
	printf("\n");
	printf("%s", "2. la moltiplicazione di due numeri utilizzando la somma");
	printf("\n");
	printf("%s", "3. la divisione intera fra due numeri positivi");
	printf("\n");
	printf("%s", "4. l’elevamento a potenza");
	printf("\n");
	printf("%s", "5. la successione di Fibonacci");
	printf("\n");
	printf("%s", "");
	printf("\n");
	scanf(" %s", ans);
	fflush(stdin);
;
	if(strcmp(ans, "1") ==  0){

		printf("inserisci il primo valore:	");
		scanf(" %lf", &real_a);
		fflush(stdin);
;
		printf("inserisci il secondo valore:	");
		scanf(" %lf", &real_b);
		fflush(stdin);
;
		real_result = 		add(real_a, real_b);
	}
;
	if(strcmp(ans, "2") ==  0){

		printf("inserisci il primo valore:	");
		scanf(" %d", &int_a);
		fflush(stdin);
;
		printf("inserisci il secondo valore:	");
		scanf(" %d", &int_b);
		fflush(stdin);
;
		int_result = 		mult(int_a, int_b);
	}
;
	if(strcmp(ans, "3") ==  0){

		printf("inserisci il primo valore:	");
		scanf(" %lf", &real_a);
		fflush(stdin);
;
		printf("inserisci il secondo valore:	");
		scanf(" %lf", &real_b);
		fflush(stdin);
;
		int_result = 		division(real_a, real_b);
	}
;
	if(strcmp(ans, "4") ==  0){

		printf("inserisci il primo valore:	");
		scanf(" %lf", &real_a);
		fflush(stdin);
;
		printf("inserisci il secondo valore:	");
		scanf(" %lf", &real_b);
		fflush(stdin);
;
		real_result = 		myPow(real_a, real_b);
	}
;
	if(strcmp(ans, "5") ==  0){

		printf("inserisci l'n-esimo numero di fibonacci che si vuole ottenere:	");
		scanf(" %d", &int_a);
		fflush(stdin);
;
		int_result = 		fibonacci(int_a);
	}
;
	printf("%s", strconcat("Real result: ", dtos(real_result)));
	printf("\n");
	printf("%s", strconcat("Int result: ", itos(int_result)));
	printf("\n");
}