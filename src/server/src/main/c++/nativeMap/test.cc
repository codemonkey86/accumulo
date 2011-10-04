#include "NativeMap.h"
#include "util.h"
#include "Key.h"
#include <stdio.h>

void runTest(int numRows, int numCf, int numCq, int rowLen, int cfLen, int cqLen, int cvLen, int valLen, bool testOld){

	map<std::string, std::string> tm;
	tm.insert(pair<string, string>("a","b"));
	tm.insert(pair<string, string>("a","c")).first->second="c";

	cout << tm["a"] << std::endl;

	size_t initialMemUsage = getMemUsage();

	NativeMap nm(1<<17, 1<<11);

	cout << " size pair<Key, Field>    : " << sizeof(std::pair<Key, Field>) << endl;
	cout << " size pair<Field, CMT>    : " << sizeof(std::pair<Field, CMT>) << endl;
	cout << " size pair<SubKey, Field> : " << sizeof(std::pair<SubKey, Field>) << endl;

//	std::less<Key> comp;
//	map<Key, Field, std::less<Key>, block_allocator<std::pair<Key, Field> > > oldMap(comp, block_allocator<std::pair<Key, Field> >(&lba));
	map<Key, Field> oldMap;

	int entries = 0;


	char rowFmt[16];
	char cfFmt[16];
	char cqFmt[16];
	char cvFmt[16];
	char valFmt[16];

	sprintf(rowFmt, "%s0%dd", "%", rowLen);
	sprintf(cfFmt, "%s0%dd", "%", cfLen);
	sprintf(cqFmt, "%s0%dd", "%", cqLen);
	sprintf(cvFmt, "%s0%dd", "%", cvLen);
	sprintf(valFmt, "%s0%dd", "%", valLen);
	
	for(int r = 0; r < numRows; r++){
		char row[rowLen+1];
		snprintf(row, rowLen+1, rowFmt, r);

		CMT *cmt = NULL;
		if(!testOld){
			cmt = nm.startUpdate(row);
		}

		for(int cf = 0; cf < numCf; cf++){
			char colf[cfLen+1];
			snprintf(colf, cfLen+1, cfFmt, cf);

			for(int cq = 0; cq < numCq; cq++){
				char colq[cqLen+1];
				snprintf(colq, cqLen+1, cqFmt, cq);

				char colv[cvLen+1];
				snprintf(colv, cvLen+1, cvFmt, 1);

				char val[valLen+1];
				snprintf(val, valLen+1, valFmt, entries);
				
				if(!testOld){	
					nm.update(cmt, colf, colq, colv, 5, false, val, valLen);	
				}else{
					oldMap.insert(pair<Key, Field>(Key(row, colf, colq, colv, 5, false), Field(val)));
				}


				entries++;
	
			}
		}
	}

	int expectedRowBytes = rowLen * numRows;
	int expectedEntryBytes = entries * (cfLen + cqLen + cvLen + 9 + valLen);

	cout << "row bytes   : " <<  expectedRowBytes << endl;
	cout << "entry bytes : " <<  expectedEntryBytes << endl;
	cout << "count : " << nm.count << "   " << entries << endl;
	size_t memUsage = getMemUsage();
	cout << " mem delta " << memUsage - initialMemUsage << endl;
	cout << " mem usage " << memUsage << endl;
	cout << " simple overhead " << ( (getMemUsage() - initialMemUsage) - (entries * (rowLen + cfLen + cqLen + cvLen + 9 + valLen) ) ) / ((double)entries) << endl;

	if(testOld){
		map<Key, Field>::iterator iter = oldMap.begin();
		while(iter != oldMap.end()){
			delete(iter->first.keyData);
			delete(iter->second.field);
			iter++;
		}
	}

/*

	int32_t ia[6];
	Iterator *iter = nm.iterator(ia);

	while(!iter->atEnd()){
		const Field &row = iter->rowIter->first;
		const SubKey &sk = iter->colIter->first;
		const Field &value = iter->colIter->second;
		cout << row.toString() << " " << sk.toString() << " " << value.toString() <<  endl;	
	
		iter->advance(ia);
	}

	delete(iter);
*/
}

int main(int argc, char **argv){
	int numRows,numCf, numCq, rowLen, cfLen, cqLen, cvLen, valLen, testOld;

	if(argc != 10){
		cout << "Usage : " << argv[0] << " <numRows> <numCf> <numCq> <rowLen> <cfLen> <cqLen> <cvLen> <valLen> <testOld>" << endl;
		return -1;
	}	

	sscanf(argv[1], "%d", &numRows);
	sscanf(argv[2], "%d", &numCf);
	sscanf(argv[3], "%d", &numCq);
	sscanf(argv[4], "%d", &rowLen);
	sscanf(argv[5], "%d", &cfLen);
	sscanf(argv[6], "%d", &cqLen);
	sscanf(argv[7], "%d", &cvLen);
	sscanf(argv[8], "%d", &valLen);
	sscanf(argv[9], "%d", &testOld);
	
	for(int i = 0; i < 3; i++){
		runTest(numRows,numCf, numCq, rowLen, cfLen, cqLen, cvLen, valLen, testOld);
	}
}
