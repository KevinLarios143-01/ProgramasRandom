/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int COMMENT_SINGLE_LINE = 2;
	private final int STRING_STATE = 1;
	private final int COMMENT_MULTILINE = 3;
	private final int yy_state_dtrans[] = {
		0,
		62,
		83,
		87
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"56:9,60,55,58,60,57,56:18,61,56,59,56:5,8,9,3,1,11,2,13,4,37:10,12,10,6,7,6" +
"2,56,14,38,39,40,41,42,30,39,43,44,39:2,45,39,46,47,48,39,49,50,20,51,52,53" +
",39:3,56:4,17,56,26,54,36,35,24,25,54,32,29,54:2,27,54,18,19,33,54,22,28,21" +
",23,34,31,54:3,16,56,15,5,56,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,169,
"0,1:2,2,3,1:2,4,5,6,1:9,7,8,9,1:12,10:2,11,10:4,12,10:11,1:9,13,14,15,12:2," +
"16,12:4,10,12:9,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36" +
",37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61" +
",62,63,64,65,66,67,68,69,70,71,72,73,74,10,75,76,77,78,79,80,81,82,83,84,85" +
",86,87,88,89,90,91,92,93,94,95,96,97,12,98,99,100,101")[0];

	private int yy_nxt[][] = unpackFromString(102,63,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,63,20,133,140:2,142,84,140," +
"144,140,88,64,146,140,148,140:2,150,21,164:2,165,164,166,164,85,134,141,89," +
"167,164:4,168,140,22,23,24,25,26,24,27,23,-1:65,28,-1:69,29,-1:55,30,-1:4,3" +
"1,-1:117,32,-1:3,33,-1:76,140:2,90,140:4,92,140:17,92,140:4,90,140:7,-1:25," +
"164:15,143,164:10,143,164:11,-1:45,21,-1:42,140:38,-1:25,140:15,120,140:10," +
"120,140:11,-1:25,164:38,-1:8,1,53:58,54,53:3,-1:17,140:8,34,140:4,34,140:24" +
",-1:25,164:12,66,164:14,66,164:10,-1:25,164:15,157,164:10,157,164:11,-1:17," +
"60,-1:53,1,55:54,56,55,-1,55:5,-1:17,140:9,102,140:2,35,140:8,102,140:5,35," +
"140:10,-1:25,164,67,164:6,68,164:2,152,164,68,164:15,67,164:3,152,164:4,-1:" +
"11,61,-1:59,1,57:2,82,57:4,86,57:46,58,57,59,57:5,-1:17,140,36,140:6,37,140" +
":2,108,140,37,140:15,36,140:3,108,140:4,-1:25,164:8,65,164:4,65,164:24,-1:2" +
"5,140:3,38:2,140:33,-1:25,164:3,71:2,164:33,-1:25,140:14,39,140:21,39,140,-" +
"1:25,164:3,69:2,164:33,-1:25,140:6,112,140:27,112,140:3,-1:25,164:14,70,164" +
":21,70,164,-1:25,140:7,114,140:17,114,140:12,-1:25,164,41,164:27,41,164:8,-" +
"1:25,140:11,116,140:21,116,140:4,-1:25,164:7,77,164:17,77,164:12,-1:25,140:" +
"9,117,140:11,117,140:16,-1:25,164:7,73,164:17,73,164:12,-1:25,140:10,118,14" +
"0:17,118,140:9,-1:25,164:19,74,164:3,74,164:14,-1:25,140:2,119,140:27,119,1" +
"40:7,-1:25,164:16,75,164:14,75,164:6,-1:25,140:3,40:2,140:33,-1:25,164:10,7" +
"6,164:17,76,164:9,-1:25,140:17,121,140:17,121,140:2,-1:25,164:11,79,164:21," +
"79,164:4,-1:25,140:12,122,140:14,122,140:10,-1:25,164:7,78,164:17,78,164:12" +
",-1:25,140:7,42,140:17,42,140:12,-1:25,164:18,80,164:5,80,164:13,-1:25,140," +
"72,140:27,72,140:8,-1:25,164:11,81,164:21,81,164:4,-1:25,140:7,43,140:17,43" +
",140:12,-1:25,140:19,44,140:3,44,140:14,-1:25,140:11,125,140:21,125,140:4,-" +
"1:25,140:16,45,140:14,45,140:6,-1:25,140:7,126,140:17,126,140:12,-1:25,140:" +
"2,127,140:27,127,140:7,-1:25,140:10,128,140:17,128,140:9,-1:25,140:10,46,14" +
"0:17,46,140:9,-1:25,140:7,47,140:17,47,140:12,-1:25,140:7,48,140:17,48,140:" +
"12,-1:25,140:5,139,140:26,139,140:5,-1:25,140:12,130,140:14,130,140:10,-1:2" +
"5,140:7,49,140:17,49,140:12,-1:25,140:11,50,140:21,50,140:4,-1:25,140:18,51" +
",140:5,51,140:13,-1:25,140:3,132:2,140:33,-1:25,140:11,52,140:21,52,140:4,-" +
"1:25,140:5,94,140:9,96,140:10,96,140:5,94,140:5,-1:25,164:2,153,164:4,91,16" +
"4:17,91,164:4,153,164:7,-1:25,140:11,124,140:21,124,140:4,-1:25,140:9,138,1" +
"40:11,138,140:16,-1:25,140:2,123,140:27,123,140:7,-1:25,140:11,129,140:21,1" +
"29,140:4,-1:25,140:12,131,140:14,131,140:10,-1:25,164:2,93,164:4,95,164:17," +
"95,164:4,93,164:7,-1:25,140:10,98,100,140:16,98,140:4,100,140:4,-1:25,164:7" +
",97,164:17,97,164:12,-1:25,140:2,104,140:4,106,140:17,106,140:4,104,140:7,-" +
"1:25,164:11,99,164:21,99,164:4,-1:25,140:15,110,140:10,110,140:11,-1:25,164" +
":9,156,164:11,156,164:16,-1:25,140:2,137,140:27,137,140:7,-1:25,164:11,101," +
"164:21,101,164:4,-1:25,140:9,135,136,140:10,135,140:6,136,140:9,-1:25,164:9" +
",103,164:11,103,164:16,-1:25,164:17,158,164:17,158,164:2,-1:25,164:2,105,16" +
"4:27,105,164:7,-1:25,164:2,107,164:27,107,164:7,-1:25,164:12,159,164:14,159" +
",164:10,-1:25,164:11,109,164:21,109,164:4,-1:25,164:7,160,164:17,160,164:12" +
",-1:25,164:2,161,164:27,161,164:7,-1:25,164:10,111,164:17,111,164:9,-1:25,1" +
"64:5,162,164:26,162,164:5,-1:25,164:12,113,164:14,113,164:10,-1:25,164:12,1" +
"63,164:14,163,164:10,-1:25,164:3,115:2,164:33,-1:25,164:9,145,147,164:10,14" +
"5,164:6,147,164:9,-1:25,164:10,149,151,164:16,149,164:4,151,164:4,-1:25,164" +
":2,154,164:27,154,164:7,-1:25,164:15,155,164:10,155,164:11,-1:8");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ 
    return new Symbol(TokenConstants.PLUS);
}
					case -3:
						break;
					case 3:
						{ 
    return new Symbol(TokenConstants.MINUS);
}
					case -4:
						break;
					case 4:
						{ 
    return new Symbol(TokenConstants.MULT);
}
					case -5:
						break;
					case 5:
						{ 
    return new Symbol(TokenConstants.DIV);
}
					case -6:
						break;
					case 6:
						{ 
    return new Symbol(TokenConstants.NEG);
}
					case -7:
						break;
					case 7:
						{ 
    return new Symbol(TokenConstants.LT);
}
					case -8:
						break;
					case 8:
						{ 
    return new Symbol(TokenConstants.EQ);
}
					case -9:
						break;
					case 9:
						{ 
    return new Symbol(TokenConstants.LPAREN);
}
					case -10:
						break;
					case 10:
						{ 
    return new Symbol(TokenConstants.RPAREN);
}
					case -11:
						break;
					case 11:
						{
     return new Symbol(TokenConstants.SEMI);
}
					case -12:
						break;
					case 12:
						{ 
    return new Symbol(TokenConstants.COMMA);
}
					case -13:
						break;
					case 13:
						{ 
    return new Symbol(TokenConstants.COLON);
}
					case -14:
						break;
					case 14:
						{ 
    return new Symbol(TokenConstants.DOT);
}
					case -15:
						break;
					case 15:
						{ 
    return new Symbol(TokenConstants.AT);
}
					case -16:
						break;
					case 16:
						{
    return new Symbol(TokenConstants.RBRACE);
}
					case -17:
						break;
					case 17:
						{ 
    return new Symbol(TokenConstants.LBRACE);
}
					case -18:
						break;
					case 18:
						{ 
    return new Symbol(TokenConstants.ERROR, yytext()); 
}
					case -19:
						break;
					case 19:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -20:
						break;
					case 20:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -21:
						break;
					case 21:
						{  
    return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
}
					case -22:
						break;
					case 22:
						{
    curr_lineno++;
}
					case -23:
						break;
					case 23:
						{ 
    return new Symbol(TokenConstants.ERROR, yytext());
}
					case -24:
						break;
					case 24:
						{ }
					case -25:
						break;
					case 25:
						{ }
					case -26:
						break;
					case 26:
						{
    contador_string = 0;
    str_acumulador="";
    null_character = 0;
    yybegin(STRING_STATE);
}
					case -27:
						break;
					case 27:
						{ }
					case -28:
						break;
					case 28:
						{
    yybegin(COMMENT_SINGLE_LINE);
}
					case -29:
						break;
					case 29:
						{ 
    return new Symbol(TokenConstants.ERROR, "Unmatched *)"); 
}
					case -30:
						break;
					case 30:
						{
    return new Symbol(TokenConstants.ASSIGN);
}
					case -31:
						break;
					case 31:
						{ 
    return new Symbol(TokenConstants.LE);
}
					case -32:
						break;
					case 32:
						{ 
    return new Symbol(TokenConstants.DARROW); 
}
					case -33:
						break;
					case 33:
						{
    abrimos_comentario=true;
    cantidad_comentarios_abiertos++;
    yybegin(COMMENT_MULTILINE);
}
					case -34:
						break;
					case 34:
						{
    return new Symbol(TokenConstants.OF);
}
					case -35:
						break;
					case 35:
						{
    return new Symbol(TokenConstants.FI);
}
					case -36:
						break;
					case 36:
						{
    return new Symbol(TokenConstants.IN);
}
					case -37:
						break;
					case 37:
						{
    return new Symbol(TokenConstants.IF);
}
					case -38:
						break;
					case 38:
						{
    return new Symbol(TokenConstants.NOT);
}
					case -39:
						break;
					case 39:
						{
    return new Symbol(TokenConstants.NEW);
}
					case -40:
						break;
					case 40:
						{
    return new Symbol(TokenConstants.LET);
}
					case -41:
						break;
					case 41:
						{
    return new Symbol(TokenConstants.THEN);
}
					case -42:
						break;
					case 42:
						{
    return new Symbol(TokenConstants.BOOL_CONST,true);
}
					case -43:
						break;
					case 43:
						{
    return new Symbol(TokenConstants.ELSE);
}
					case -44:
						break;
					case 44:
						{
    return new Symbol(TokenConstants.ESAC);
}
					case -45:
						break;
					case 45:
						{
    return new Symbol(TokenConstants.LOOP);
}
					case -46:
						break;
					case 46:
						{
    return new Symbol(TokenConstants.POOL);
}
					case -47:
						break;
					case 47:
						{
    return new Symbol(TokenConstants.CASE);
}
					case -48:
						break;
					case 48:
						{
    return new Symbol(TokenConstants.BOOL_CONST,false);
}
					case -49:
						break;
					case 49:
						{
    return new Symbol(TokenConstants.WHILE);
}
					case -50:
						break;
					case 50:
						{
    return new Symbol(TokenConstants.CLASS);
}
					case -51:
						break;
					case 51:
						{
    return new Symbol(TokenConstants.ISVOID);
}
					case -52:
						break;
					case 52:
						{
    return new Symbol(TokenConstants.INHERITS);
}
					case -53:
						break;
					case 53:
						{
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
					case -54:
						break;
					case 54:
						{
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
					case -55:
						break;
					case 55:
						{ }
					case -56:
						break;
					case 56:
						{
    curr_lineno++;
    yybegin(YYINITIAL);
}
					case -57:
						break;
					case 57:
						{}
					case -58:
						break;
					case 58:
						{
    curr_lineno++;
}
					case -59:
						break;
					case 59:
						{}
					case -60:
						break;
					case 60:
						{
    if(cantidad_comentarios_abiertos>1){
        cantidad_comentarios_abiertos -= 1;
    }else if(abrimos_comentario==true && cantidad_comentarios_abiertos==1){
        //curr_lineno++;
        cantidad_comentarios_abiertos=0;
        abrimos_comentario=false;
        yybegin(YYINITIAL);   
    }   
}
					case -61:
						break;
					case 61:
						{
    cantidad_comentarios_abiertos++;
}
					case -62:
						break;
					case 63:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -63:
						break;
					case 64:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -64:
						break;
					case 65:
						{
    return new Symbol(TokenConstants.OF);
}
					case -65:
						break;
					case 66:
						{
    return new Symbol(TokenConstants.FI);
}
					case -66:
						break;
					case 67:
						{
    return new Symbol(TokenConstants.IN);
}
					case -67:
						break;
					case 68:
						{
    return new Symbol(TokenConstants.IF);
}
					case -68:
						break;
					case 69:
						{
    return new Symbol(TokenConstants.NOT);
}
					case -69:
						break;
					case 70:
						{
    return new Symbol(TokenConstants.NEW);
}
					case -70:
						break;
					case 71:
						{
    return new Symbol(TokenConstants.LET);
}
					case -71:
						break;
					case 72:
						{
    return new Symbol(TokenConstants.THEN);
}
					case -72:
						break;
					case 73:
						{
    return new Symbol(TokenConstants.ELSE);
}
					case -73:
						break;
					case 74:
						{
    return new Symbol(TokenConstants.ESAC);
}
					case -74:
						break;
					case 75:
						{
    return new Symbol(TokenConstants.LOOP);
}
					case -75:
						break;
					case 76:
						{
    return new Symbol(TokenConstants.POOL);
}
					case -76:
						break;
					case 77:
						{
    return new Symbol(TokenConstants.CASE);
}
					case -77:
						break;
					case 78:
						{
    return new Symbol(TokenConstants.WHILE);
}
					case -78:
						break;
					case 79:
						{
    return new Symbol(TokenConstants.CLASS);
}
					case -79:
						break;
					case 80:
						{
    return new Symbol(TokenConstants.ISVOID);
}
					case -80:
						break;
					case 81:
						{
    return new Symbol(TokenConstants.INHERITS);
}
					case -81:
						break;
					case 82:
						{}
					case -82:
						break;
					case 84:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -83:
						break;
					case 85:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -84:
						break;
					case 86:
						{}
					case -85:
						break;
					case 88:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -86:
						break;
					case 89:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -87:
						break;
					case 90:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -88:
						break;
					case 91:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -89:
						break;
					case 92:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -90:
						break;
					case 93:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -91:
						break;
					case 94:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -92:
						break;
					case 95:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -93:
						break;
					case 96:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -94:
						break;
					case 97:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -95:
						break;
					case 98:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -96:
						break;
					case 99:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -97:
						break;
					case 100:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -98:
						break;
					case 101:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -99:
						break;
					case 102:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -100:
						break;
					case 103:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -101:
						break;
					case 104:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -102:
						break;
					case 105:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -103:
						break;
					case 106:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -104:
						break;
					case 107:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -105:
						break;
					case 108:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -106:
						break;
					case 109:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -107:
						break;
					case 110:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -108:
						break;
					case 111:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -109:
						break;
					case 112:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -110:
						break;
					case 113:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -111:
						break;
					case 114:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -112:
						break;
					case 115:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -113:
						break;
					case 116:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -114:
						break;
					case 117:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -115:
						break;
					case 118:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -116:
						break;
					case 119:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -117:
						break;
					case 120:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -118:
						break;
					case 121:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -119:
						break;
					case 122:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -120:
						break;
					case 123:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -121:
						break;
					case 124:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -122:
						break;
					case 125:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -123:
						break;
					case 126:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -124:
						break;
					case 127:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -125:
						break;
					case 128:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -126:
						break;
					case 129:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -127:
						break;
					case 130:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -128:
						break;
					case 131:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -129:
						break;
					case 132:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -130:
						break;
					case 133:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -131:
						break;
					case 134:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -132:
						break;
					case 135:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -133:
						break;
					case 136:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -134:
						break;
					case 137:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -135:
						break;
					case 138:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -136:
						break;
					case 139:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -137:
						break;
					case 140:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -138:
						break;
					case 141:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -139:
						break;
					case 142:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -140:
						break;
					case 143:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -141:
						break;
					case 144:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -142:
						break;
					case 145:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -143:
						break;
					case 146:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -144:
						break;
					case 147:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -145:
						break;
					case 148:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -146:
						break;
					case 149:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -147:
						break;
					case 150:
						{
    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -148:
						break;
					case 151:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -149:
						break;
					case 152:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -150:
						break;
					case 153:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -151:
						break;
					case 154:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -152:
						break;
					case 155:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -153:
						break;
					case 156:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -154:
						break;
					case 157:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -155:
						break;
					case 158:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -156:
						break;
					case 159:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -157:
						break;
					case 160:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -158:
						break;
					case 161:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -159:
						break;
					case 162:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -160:
						break;
					case 163:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -161:
						break;
					case 164:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -162:
						break;
					case 165:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -163:
						break;
					case 166:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -164:
						break;
					case 167:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -165:
						break;
					case 168:
						{
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -166:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
