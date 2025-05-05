import React, { useState } from 'react';
import { Box, Button, Container, TextField, Typography, Paper, List, ListItem, ListItemText } from '@mui/material';
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_GATEWAY || 'http://localhost:8080';

const ProductCodePage = () => {
  const [code, setCode] = useState('');
  const [result, setResult] = useState(null);
  const [products, setProducts] = useState([]);
  const [listA, setListA] = useState('');
  const [listB, setListB] = useState('');
  const [operationResult, setOperationResult] = useState([]);

  const handleCheckPriority = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/product-codes/${code}/is-priority`);
      setResult(`Is Priority: ${response.data}`);
    } catch (error) {
      setResult('Error checking priority');
    }
  };

  const handleVerify = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/product-codes/${code}/verify`);
      setResult(`Verification: ${response.data}`);
    } catch (error) {
      setResult('Error verifying code');
    }
  };

  const handleSort = async () => {
    try {
      const productList = products.split(',').map(p => p.trim());
      const response = await axios.post(`${API_BASE_URL}/api/product-codes/sort`, productList);
      setOperationResult(response.data);
    } catch (error) {
      setResult('Error sorting products');
    }
  };

  const handleUnion = async () => {
    try {
      const list1 = listA.split(',').map(p => p.trim());
      const list2 = listB.split(',').map(p => p.trim());
      const response = await axios.post(`${API_BASE_URL}/api/product-codes/union'`, [list1, list2]);
      setOperationResult(response.data);
    } catch (error) {
      setResult('Error calculating union');
    }
  };

  const handleIntersection = async () => {
    try {
      const list1 = listA.split(',').map(p => p.trim());
      const list2 = listB.split(',').map(p => p.trim());
      const response = await axios.post(`${API_BASE_URL}/api/product-codes/intersection`, [list1, list2]);
      setOperationResult(response.data);
    } catch (error) {
      setResult('Error calculating intersection');
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Product Code Operations</Typography>
      
      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h6">Single Code Operations</Typography>
        <TextField
          label="Product Code"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          fullWidth
          margin="normal"
        />
        <Box sx={{ display: 'flex', gap: 2, mt: 2 }}>
          <Button variant="contained" onClick={handleCheckPriority}>Check Priority</Button>
          <Button variant="contained" onClick={handleVerify}>Verify Code</Button>
        </Box>
        {result && <Typography sx={{ mt: 2 }}>{result}</Typography>}
      </Paper>

      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h6">Sort Products</Typography>
        <TextField
          label="Product List (comma separated)"
          value={products}
          onChange={(e) => setProducts(e.target.value)}
          fullWidth
          margin="normal"
        />
        <Button variant="contained" onClick={handleSort} sx={{ mt: 2 }}>Sort</Button>
      </Paper>

      <Paper sx={{ p: 2 }}>
        <Typography variant="h6">Set Operations</Typography>
        <TextField
          label="List A (comma separated)"
          value={listA}
          onChange={(e) => setListA(e.target.value)}
          fullWidth
          margin="normal"
        />
        <TextField
          label="List B (comma separated)"
          value={listB}
          onChange={(e) => setListB(e.target.value)}
          fullWidth
          margin="normal"
        />
        <Box sx={{ display: 'flex', gap: 2, mt: 2 }}>
          <Button variant="contained" onClick={handleUnion}>Union</Button>
          <Button variant="contained" onClick={handleIntersection}>Intersection</Button>
        </Box>
      </Paper>

      {operationResult.length > 0 && (
        <Paper sx={{ p: 2, mt: 2 }}>
          <Typography variant="h6">Result</Typography>
          <List>
            {operationResult.map((item, index) => (
              <ListItem key={index}>
                <ListItemText primary={item} />
              </ListItem>
            ))}
          </List>
        </Paper>
      )}
    </Container>
  );
};

export default ProductCodePage;