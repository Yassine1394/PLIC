// change data: feature*sample -> sample->feature, and put the class label to the last column;
void TransposForDiscretize(char* filename)
{
    int r_row = 0;
    int r_col = 0;
    double** r_data;

    char *a;
    char buffer[8192];
    int i, j, k=0;
    
    ifstream inn(filename);
    if(!inn) 
    {
        cout<<"打开文件失败!"<<endl;
        return;
    }
    inn.getline(buffer, 8192 );
    a = strtok( buffer,",");
    while (a!=NULL)
    {
        r_row++;
        a = strtok(NULL,",");       
    }
    r_row = r_row-1;
    // cout<<r_row<<endl;
    
    while (inn.getline(buffer,8192))
    {
        r_col++;
    }
    inn.close();
    r_data = new double*[r_row];
    for ( i=0; i<r_row; i++ )
    {   
        r_data[i] = new double[r_col];
    }   
    
    ifstream in(filename);
    if(!in) 
    {
        cout<<"打开文件失败!"<<endl;
        return;
    }
    in.getline( buffer, 8192 );
    
    int* label=new int[r_row];
    a = strtok( buffer,",");
    int i_label=0;
    while (a = strtok( NULL,","))
        label[i_label++]=atoi(a);
    j=0;
    while (in.getline(buffer,8192))
    {
        a = strtok( buffer,",");
        i=0;
        while (a!=NULL)
        {
            a = strtok(NULL,",");
            if (a!=NULL)
            {
                r_data[i][j] = atof(a); 
                i++;
            }
        }
        j++;
        a = strtok( NULL,",");
    }
    in.close();
    
    ofstream outt(filename);
    for (i = 0; i < r_col; ++i)
        outt << i + 1 << ',';
    outt << "label" << endl;
    for (i = 0; i < r_row; ++i)
    {
        for (j = 0; j < r_col; ++j)
            outt << r_data[i][j] << ',';
        outt << label[i] << endl;
    }
    outt.close();
    r_col=0;
    for(i=0;i<r_row;i++)
        delete[] r_data[i];
    delete[] r_data;
    delete[] label;
    r_row=0;
}