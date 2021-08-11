/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }

    String str_acumulador="";
    String str_aux = "";
    int contador_string = 0;
    int int_str_aux = 0;
    String comentarios_acumuladores="";
    boolean abrimos_comentario=false;
    int cantidad_comentarios_abiertos=0;
    int null_character = 0;
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
        case STRING_STATE:
        yybegin(YYINITIAL);
        curr_lineno++;
        return new Symbol(TokenConstants.ERROR, "EOF in string constant");
        case COMMENT_MULTILINE:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "Unmatched *)");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state STRING_STATE
%state COMMENT_SINGLE_LINE
%state COMMENT_MULTILINE




%%

<YYINITIAL>[+]  { 
    return new Symbol(TokenConstants.PLUS);
}

<YYINITIAL>[-]  { 
    return new Symbol(TokenConstants.MINUS);
}

<YYINITIAL>[*]  { 
    return new Symbol(TokenConstants.MULT);
}

<YYINITIAL>[/]  { 
    return new Symbol(TokenConstants.DIV);
}

<YYINITIAL>[~]  { 
    return new Symbol(TokenConstants.NEG);
}

<YYINITIAL>[<]  { 
    return new Symbol(TokenConstants.LT);
}

<YYINITIAL>[=]  { 
    return new Symbol(TokenConstants.EQ);
}

<YYINITIAL>[(]  { 
    return new Symbol(TokenConstants.LPAREN);
}

<YYINITIAL>[)]  { 
    return new Symbol(TokenConstants.RPAREN);
}

<YYINITIAL>[;]  {
     return new Symbol(TokenConstants.SEMI);
}

<YYINITIAL>[,]  { 
    return new Symbol(TokenConstants.COMMA);
}

<YYINITIAL>[:]  { 
    return new Symbol(TokenConstants.COLON);
}

<YYINITIAL>[.]  { 
    return new Symbol(TokenConstants.DOT);
}

<YYINITIAL>[@]  { 
    return new Symbol(TokenConstants.AT);
}

<YYINITIAL>[}]  {
    return new Symbol(TokenConstants.RBRACE);
}

<YYINITIAL>[{]  { 
    return new Symbol(TokenConstants.LBRACE);
}

<YYINITIAL>[<][-]  {
    return new Symbol(TokenConstants.ASSIGN);
}

<YYINITIAL>[<][=]  { 
    return new Symbol(TokenConstants.LE);
}

<YYINITIAL>[_] { 
    return new Symbol(TokenConstants.ERROR, yytext()); 
}



<YYINITIAL>[nN][Oo][Tt]  {
    return new Symbol(TokenConstants.NOT);
}

<YYINITIAL>[t][Rr][Uu][Ee]  {
    return new Symbol(TokenConstants.BOOL_CONST,true);
}

<YYINITIAL>[f][Aa][Ll][Ss][Ee]  {
    return new Symbol(TokenConstants.BOOL_CONST,false);
}

<YYINITIAL>[Ee][Ll][Ss][Ee] {
    return new Symbol(TokenConstants.ELSE);
}

<YYINITIAL>[Ii][Ff] {
    return new Symbol(TokenConstants.IF);
}

<YYINITIAL>[Ll][Ee][Tt] {
    return new Symbol(TokenConstants.LET);
}

<YYINITIAL>[Ii][Nn] {
    return new Symbol(TokenConstants.IN);
}

<YYINITIAL>[Ff][Ii] {
    return new Symbol(TokenConstants.FI);
}

<YYINITIAL>[Nn][Ee][Ww] {
    return new Symbol(TokenConstants.NEW);
}

<YYINITIAL>[Tt][Hh][Ee][Nn] {
    return new Symbol(TokenConstants.THEN);
}

<YYINITIAL>[Ww][Hh][Ii][Ll][Ee] {
    return new Symbol(TokenConstants.WHILE);
}

<YYINITIAL>[Ll][Oo][Oo][Pp] {
    return new Symbol(TokenConstants.LOOP);
}

<YYINITIAL>[Ii][Ss][Vv][Oo][Ii][Dd] {
    return new Symbol(TokenConstants.ISVOID);
}

<YYINITIAL>[Pp][Oo][Oo][Ll] {
    return new Symbol(TokenConstants.POOL);
}

<YYINITIAL>[Cc][Ll][Aa][Ss][Ss] {
    return new Symbol(TokenConstants.CLASS);
}

<YYINITIAL>[Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss] {
    return new Symbol(TokenConstants.INHERITS);
}

<YYINITIAL>[Ee][Ss][Aa][Cc] {
    return new Symbol(TokenConstants.ESAC);
}

<YYINITIAL>[Cc][Aa][Ss][Ee] {
    return new Symbol(TokenConstants.CASE);
}

<YYINITIAL>[Oo][Ff] {
    return new Symbol(TokenConstants.OF);
}

<YYINITIAL>[0-9]+ {  
    return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
}

<YYINITIAL> [A-Z][A-Za-z0-9_]* {
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}

<YYINITIAL> [a-z][A-Za-z0-9_]* {
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}

<YYINITIAL>[*][)] { 
    return new Symbol(TokenConstants.ERROR, "Unmatched *)"); 
}

<YYINITIAL>[-][-] {
    yybegin(COMMENT_SINGLE_LINE);
}

<YYINITIAL>[(][*] {
    abrimos_comentario=true;
    cantidad_comentarios_abiertos++;
    yybegin(COMMENT_MULTILINE);
}

<YYINITIAL>[\n] {
    curr_lineno++;
    
}

<COMMENT_SINGLE_LINE>. { }

<COMMENT_SINGLE_LINE>[\n] {
    curr_lineno++;
    yybegin(YYINITIAL);
}

<COMMENT_MULTILINE>[(][*] {
    cantidad_comentarios_abiertos++;
}

<COMMENT_MULTILINE> . {}
<COMMENT_MULTILINE>[\r] {}
<COMMENT_MULTILINE>[\n] {
    curr_lineno++;
}

<COMMENT_MULTILINE>[*][)] {
    if(cantidad_comentarios_abiertos>1){
        cantidad_comentarios_abiertos -= 1;
    }else if(abrimos_comentario==true && cantidad_comentarios_abiertos==1){
        //curr_lineno++;
        cantidad_comentarios_abiertos=0;
        abrimos_comentario=false;
        yybegin(YYINITIAL);   
    }   
}

<COMMENT_MULTILINE>[\x0B] { }






<YYINITIAL>["\""] {
    contador_string = 0;
    str_acumulador="";
    null_character = 0;
    yybegin(STRING_STATE);
}

<STRING_STATE>[^"\""] {
    char a = '\0';
    if(int_str_aux == 0){
        if(String.valueOf(a).equals(yytext())){
            null_character = 1;
        }
        else if(!yytext().equals("\\")){
            if(yytext().equals("\n")){
                curr_lineno++;
                yybegin(YYINITIAL);
                return new Symbol(TokenConstants.ERROR, "Unterminated string constant" );
            }else{
                str_acumulador += yytext();
                contador_string++;
            }
        }
        else{
            int_str_aux = 1;
        }
    }
    else if(int_str_aux == 1){
        
        if(String.valueOf(a).equals(yytext())){
            null_character = 1;
        }
        else if(yytext().equals("n")){
            str_acumulador += "\n";
            int_str_aux = 0;
            contador_string+=1;
        }
        else if(yytext().equals("t")){
            str_acumulador += "\t";
            int_str_aux = 0;
            contador_string+=1;
        }
        else if(yytext().equals("b")){   
            str_acumulador += "\b";
            int_str_aux = 0;
            contador_string+=1;
        }
        else if(yytext().equals("f")){
            str_acumulador += "\f";
            int_str_aux = 0;
            contador_string+=1;
        }
        else{
            str_acumulador += yytext();
            int_str_aux = 0;
            contador_string++;
        }
    }       
}


<STRING_STATE>["\""] {
    if(null_character == 1){
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "String contains null character.");
    }
    else if(contador_string >= MAX_STR_CONST){
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "String constant too long");
    }
    else if(int_str_aux == 0){
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(str_acumulador));    
    }
    else{
        str_acumulador += "\"";
        int_str_aux = 0;
    }
    
}




<YYINITIAL>[\r\v\t\f] { }
<YYINITIAL>" " { }
<YYINITIAL>[\013] { }

<YYINITIAL>"=>"			{ 
    return new Symbol(TokenConstants.DARROW); 
}

.                               { 
    return new Symbol(TokenConstants.ERROR, yytext());
}
